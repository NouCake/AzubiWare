package connection.packet;

import connection.Connection;
import connection.ConnectionListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parses the String of ConnectionListener.onMessage to a Packet with the provided PacketParser and delegates the call
 * to the right Handler Method of the provided PacketHandler.
 * If a Handler Method for the base Packet Class is provided it will be used as a default Handler for all received Packets without a Handler
 */
public class PacketHandlerAdapter implements ConnectionListener {

    private static Method[] getHandlerMethods(Class<? extends PacketHandler> handlerClass){
        ArrayList<Method> handlers = new ArrayList<>();
        for(Method m : handlerClass.getDeclaredMethods()){
            if(m.getParameterCount() != 2) continue;

            Class<?> connectionClass = m.getParameterTypes()[0];
            Class<?> packetClass = m.getParameterTypes()[1];
            if(!Connection.class.isAssignableFrom(connectionClass)) continue;
            if(!Packet.class.isAssignableFrom(packetClass)) continue;

            handlers.add(m);
        }
        return handlers.toArray(new Method[0]);
    }

    private final PacketParser parser;
    private final PacketHandler handler;

    private final HashMap<String, Method> packetHandleMethods;
    private Method defaultHandler;

    public PacketHandlerAdapter(PacketHandler handler, PacketParser parser) {
        if(handler == null) throw new IllegalArgumentException("PacketHandler cannot be null.");
        if(parser == null) throw new IllegalArgumentException("PacketParser cannot be null.");
        this.handler = handler;
        this.parser = parser;

        packetHandleMethods = new HashMap<>();
        initPacketHandlers();
    }

    private void initPacketHandlers(){
        for(Method m : getHandlerMethods(handler.getClass())){
            Class<?> packetClass = m.getParameterTypes()[1];

            if(packetClass == Packet.class) {
                defaultHandler = m;
                continue;
            }

            parser.registerPacketClass((Class<? extends Packet>) packetClass);
            String packetType = packetClass.getSimpleName();
            packetHandleMethods.put(packetType, m);
        }
    }

    @Override
    public void onMessage(Connection connection, String message) {
        Packet packet = parser.createPacketFromJson(message);
        if(packet == null) return;

        String packetType = packet.getClass().getSimpleName();
        Method handlerMethod = packetHandleMethods.get(packetType);
        if(handlerMethod == null) handlerMethod = defaultHandler;
        if(handlerMethod == null) {
            System.err.println("No Handler available for Packet: " + packetType);
            return;
        }

        try {
            handlerMethod.invoke(handler, connection, packet);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(Connection connection) {
    }

    @Override
    public void onDisconnected(Connection connection) {
    }

}

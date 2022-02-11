package connection.packet;

import connection.Connection;
import connection.ConnectionListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class PacketHandlerAdapter implements ConnectionListener {

    private final PacketParser parser;
    private final PacketHandler handler;
    private HashMap<String, Method> packetHandleMethods;
    private Method defaultHandler;

    public PacketHandlerAdapter(PacketHandler handler) {
        if(handler == null) throw new IllegalArgumentException("PacketHandler cannot be null, listener will be useless");
        this.handler = handler;

        packetHandleMethods = new HashMap<>();
        parser = new PacketParser();
        initPacketHandlers(handler);
    }

    public PacketParser getParser() {
        return parser;
    }

    private void initPacketHandlers(PacketHandler handler){
        Method[] methods = handler.getClass().getDeclaredMethods();
        for(Method m : methods){
            if(m.getParameterCount() != 2) continue;

            Class<?> connectionClass = m.getParameterTypes()[0];
            Class<?> packetClass = m.getParameterTypes()[1];
            if(!Connection.class.isAssignableFrom(connectionClass)) continue;
            if(!Packet.class.isAssignableFrom(packetClass)) continue;
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
            new RuntimeException("Could not invoke PacketHandler", e).printStackTrace();
        }
    }

    @Override
    public void onConnected(Connection connection) {}

    @Override
    public void onDisconnected(Connection connection) {}

}

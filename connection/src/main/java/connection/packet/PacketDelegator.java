package connection.packet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Finds all Handler Method in a given PacketHandler and delegates received Packets to the corresponding Handler.
 * If a Handler Method for the base Packet Class is provided it will be used as a default Handler for all received Packets without a Handler.
 *
 * A Handler Method is a method that takes 2 Parameter: Connection, ? extends Packet
 */
public class PacketDelegator<T> {

    private final Class<T> clazz;
    private final PacketParser parser;
    private final PacketHandler handler;

    private final HashMap<String, Method> packetHandleMethods;
    private Method defaultHandler;

    public PacketDelegator(Class<T> clazz, PacketHandler handler, PacketParser parser) {
        if(clazz == null) throw new IllegalArgumentException("Class cannot be null.");
        if(handler == null) throw new IllegalArgumentException("PacketHandler cannot be null.");
        this.clazz = clazz;
        this.handler = handler;
        this.parser = parser;

        packetHandleMethods = new HashMap<>();
        initPacketHandlers();
    }

    public PacketDelegator(Class<T> clazz, PacketHandler handler) {
        this(clazz, handler, null);
    }

    public void onPacket(T connection, Packet packet){
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

    private void initPacketHandlers(){
        for(Method m : getHandlerMethods(handler.getClass(), clazz)){
            Class<?> packetClass = m.getParameterTypes()[1];

            if(packetClass == Packet.class) {
                defaultHandler = m;
                continue;
            }

            if(parser != null) parser.registerPacketClass((Class<? extends Packet>) packetClass);
            String packetType = packetClass.getSimpleName();
            packetHandleMethods.put(packetType, m);
        }
    }

    public static <T> Method[] getHandlerMethods(Class<? extends PacketHandler> handlerClass, Class<T> clazz){
        ArrayList<Method> handlers = new ArrayList<>();
        for(Method m : handlerClass.getDeclaredMethods()){
            if(m.getParameterCount() != 2) continue;

            Class<?> connectionClass = m.getParameterTypes()[0];
            Class<?> packetClass = m.getParameterTypes()[1];
            if(!clazz.isAssignableFrom(connectionClass)) continue;
            if(!Packet.class.isAssignableFrom(packetClass)) continue;

            handlers.add(m);
        }
        return handlers.toArray(new Method[0]);
    }

}

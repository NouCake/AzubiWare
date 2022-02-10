package de.united.azubiware.Packets.Handler;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.IPacket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class APacketHandler implements IMessageHandler {

    private HashMap<String, Method> packetHandleMethods;
    private PacketParser parser;

    public APacketHandler(){
        parser = new PacketParser();
        initPacketHandlers();
    }

    private void initPacketHandlers(){
        packetHandleMethods = new HashMap<>();

        Method[] methods = getClass().getDeclaredMethods();
        for(Method m : methods){
            if(m.getParameterCount() != 2) continue;

            Class<?> connectionClass = m.getParameterTypes()[0];
            Class<?> packetClass = m.getParameterTypes()[1];
            if(!IConnection.class.isAssignableFrom(connectionClass)) continue;
            if(!IPacket.class.isAssignableFrom(packetClass)) continue;
            if(packetClass == IPacket.class) continue;

            parser.addPacketClass((Class<? extends IPacket>) packetClass);
            String packetType = packetClass.getSimpleName();
            System.out.println("Adding Handler: " + packetType + " " + m.getName());
            packetHandleMethods.put(packetType, m);
        }

    }

    @Override
    public void onMessage(IConnection user, String message) {
        IPacket packet = parser.createPacketFromJson(message);
        if(packet == null) {
            return;
        };

        String packetType = packet.getClass().getSimpleName();

        if(!packetHandleMethods.containsKey(packetType)) {
            return;
        }
        Method handler = packetHandleMethods.get(packetType);
        try {
            handler.invoke(this, user, packet);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while handling Packet " + packetType);
        }

    }

}

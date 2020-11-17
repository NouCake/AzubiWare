package de.united.azubiware.Packets.Handler;


import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.User.IUser;

import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class APacketHandler implements IUserPacketHandler {

    private HashMap<Integer, Method> packetHandleMethods;

    public APacketHandler(){
        initPacketHandlers();
    }

    private void initPacketHandlers(){
        packetHandleMethods = new HashMap<>();

        Method[] methods = getClass().getDeclaredMethods();
        for(Method m : methods){
            System.out.println(m.getName());
            if(m.getParameterCount() != 2)  {
                System.out.println("r0");
                continue;
            }

            Class<?> userClass = m.getParameterTypes()[0];
            Class<?> packetClass = m.getParameterTypes()[1];
            if(!userClass.isAssignableFrom(IUser.class)) continue;
            if(!IPacket.class.isAssignableFrom(packetClass)) continue;
            if(packetClass == IPacket.class) continue;

            int packetType = PacketParser.getTypeFromPacketClass(packetClass);
            System.out.println("Adding Handler: " + packetType + " " + m.getName());
            packetHandleMethods.put(packetType, m);
        }

    }

    @Override
    public void onPacket(IUser user, IPacket packet) {
        int packetType = PacketParser.getTypeFromPacketClass(packet.getClass());

        if(!packetHandleMethods.containsKey(packetType)) throw new RuntimeException("No Handler with given Packet Type");
        Method handler = packetHandleMethods.get(packetType);
        try {
            handler.invoke(this, user, packet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while handling Packet " + packetType);
        }

    }

}

package match;

import connection.Connection;
import connection.packet.Packet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MatchPacketHandlerAdapter implements MatchPacketListener{

    private final MatchPacketHandler handler;
    private final Map<String, Method> packetHandleMethods;

    public MatchPacketHandlerAdapter(MatchPacketHandler handler) {
        this.handler = handler;

        packetHandleMethods = new HashMap<>();
        initHandlerMethods();
    }

    private void initHandlerMethods(){
        for(Method m : handler.getClass().getDeclaredMethods()) {
            if(m.getParameterCount() != 2) continue;

            Class<?> playerClass = m.getParameterTypes()[0];
            Class<?> packetClass = m.getParameterTypes()[1];

            if(!MatchPlayer.class.isAssignableFrom(playerClass)) continue;
            if(!Packet.class.isAssignableFrom(packetClass)) continue;
            if(packetClass == Packet.class) continue;

            String packetType = packetClass.getSimpleName();
            packetHandleMethods.put(packetType, m);
        }
    }

    @Override
    public void onPacket(MatchPlayer player, Packet packet) {
        String packetType = packet.getClass().getSimpleName();
        Method handlerMethod = packetHandleMethods.get(packetType);
        if(handlerMethod == null) return;

        try {
            handlerMethod.invoke(handler, player, packet);
        } catch (IllegalAccessException | InvocationTargetException e) {
            new RuntimeException("Could not invoke MatchPacketHandler", e).printStackTrace();
        }
    }

    @Override
    public void onDisconnected(MatchPlayer connection) { }

}

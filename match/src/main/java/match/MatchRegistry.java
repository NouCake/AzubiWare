package match;

import connection.packet.Packet;
import connection.packet.PacketParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MatchRegistry {

    private final PacketParser parser;

    private final Map<String, Constructor<Match>> matchClasses;

    public MatchRegistry(PacketParser parser) {
        this.parser = parser;
        matchClasses = new HashMap<>();
    }

    public void registerMatch(String matchType, Class<? extends Match> matchClass, Class<? extends MatchPacketHandler> packetHandlerClass){
        matchClasses.put(matchType, getConstructor(matchClass));
        Method[] packetHandlers = MatchPacketHandlerAdapter.getHandlerMethods(packetHandlerClass);
        for(Method m : packetHandlers) {
            Class<?> packetClass = m.getParameterTypes()[1];
            parser.registerPacketClass((Class<? extends Packet>) packetClass);
        }
    }

    private Constructor<Match> getConstructor(Class<? extends Match> matchClass){
        for(Constructor<?> c : matchClass.getConstructors()){
            if(c.getParameterCount() != 2) continue;

            Class<?> adapterClass = c.getParameterTypes()[0];
            Class<?> playerArrayClass = c.getParameterTypes()[1];
            if(!MatchConnectionAdapter.class.isAssignableFrom(adapterClass)) continue;
            if(!MatchPlayer[].class.isAssignableFrom(playerArrayClass)) continue;
            return (Constructor<Match>) c;
        }
        return null;
    }

    public Match createMatch(String matchType, MatchConnectionAdapter adapter, MatchPlayer[] players){
        Constructor<Match> constr = matchClasses.get(matchType);
        if(constr == null) {
            throw new IllegalArgumentException("MatchType not in MatchRegistry");
        }

        try {
            return constr.newInstance(adapter, players);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

}

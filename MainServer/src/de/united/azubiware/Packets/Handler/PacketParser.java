package de.united.azubiware.Packets.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import de.united.azubiware.Packets.IPacket;
import org.reflections.Reflections;

import java.util.HashMap;

public class PacketParser {

    private static HashMap<Integer, Class<?>> packetClasses = new HashMap<>();
    private final static Gson gson = new Gson();
    static {
        collectTypeClasses();
    }

    public static int getTypeFromPacketClass(Class<?> c){
        try {
            return  c.getField("type").getInt(null);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get typeID from " + c.getSimpleName());
        }
    }
    private static void collectTypeClasses(){
        Reflections r = new Reflections("de.united.azubiware.Packets");
        r.getSubTypesOf(IPacket.class).forEach(c -> {
            int packetTypeID = getTypeFromPacketClass(c);
            if(packetClasses.containsKey(packetTypeID))
                throw new RuntimeException("Multiple Packets with same TypeID: " + c.getSimpleName());
            packetClasses.put(packetTypeID, c);
        });
    }

    public static IPacket createPacketFromJson(String jsonString){
        try{
            JsonElement json = gson.fromJson(jsonString, JsonElement.class).getAsJsonObject();

            int packetType = json.getAsJsonObject().get("type").getAsInt();
            if(!packetClasses.containsKey(packetType)) return null;

            Class<?> packetClass = packetClasses.get(packetType);
            return (IPacket) gson.fromJson(json, packetClass);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}

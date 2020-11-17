package de.united.azubiware.Packets.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import de.united.azubiware.Packets.Handler.IPacket;
import org.reflections.Reflections;

import java.util.HashMap;

public class PacketParser {

    private final static Gson gson = new Gson();
    private static HashMap<Integer, Class<?>> packetClasses = new HashMap<>();

    static {
        Reflections r = new Reflections("de.united.azubiware.Packets");
        r.getSubTypesOf(IPacket.class).forEach(c -> {
            System.out.println("Class found: " + c.getSimpleName());

            int packetTypeID;
            try {
                packetTypeID = (int)c.getField("typeID").getInt(null);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't get typeID");
            }

            if(packetClasses.containsKey(packetTypeID)) throw new RuntimeException("Multiple Packets with same TypeID: " + c.getSimpleName());
            packetClasses.put(packetTypeID, c);
        });
    }

    public static IPacket createPacketFromJson(String jsonString){
        JsonElement json = gson.fromJson(jsonString, JsonElement.class).getAsJsonObject();
        int packetType = json.getAsJsonObject().get("type").getAsInt();

        if(!packetClasses.containsKey(packetType)) return null;

        Class<?> packetClass = packetClasses.get(packetType);
        try{
            return (IPacket) gson.fromJson(json, packetClass);
        } catch (Exception e){
            return null;
        }
    }

}

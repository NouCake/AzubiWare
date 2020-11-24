package de.united.azubiware.Packets.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import de.united.azubiware.Packets.IPacket;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

public class PacketParser {

    private final HashMap<String, Class<?>> packetClasses;
    private final Gson gson;

    public PacketParser(){
        packetClasses = new HashMap<>();
        gson = new Gson();
    }

    public void addPacketClass(Class<? extends IPacket> c){
        System.out.println("Calling Consumer with: " + c.getClass());
        String packetType = c.getSimpleName();
        if(packetClasses.containsKey(packetType))
            throw new RuntimeException("Multiple Packets with same Class Name: " + c.getSimpleName());
        packetClasses.put(packetType, c);
        //System.out.println("PacketParser: register packet " + c.getSimpleName());
    }

    public IPacket createPacketFromJson(String jsonString){
        try{
            JsonElement json = gson.fromJson(jsonString, JsonElement.class);

            String packetType = json.getAsJsonObject().get("type").getAsString();
            if(!packetClasses.containsKey(packetType)) {
                //System.out.println("PacketParserError : No PacketClass for type: " + packetType);
                return null;
            };
            json.getAsJsonObject().remove("type");

            Class<?> packetClass = packetClasses.get(packetType);
            return (IPacket) gson.fromJson(json, packetClass);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}

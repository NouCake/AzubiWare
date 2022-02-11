package connection.packet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

public class PacketParser {

    private final HashMap<String, Class<?>> packetClasses;
    private final Gson gson;

    public PacketParser(){
        packetClasses = new HashMap<>();
        gson = new Gson();
    }

    public void registerPacketClass(Class<? extends Packet> c){
        System.out.println("Calling Consumer with: " + c);
        String packetType = c.getSimpleName();

        if(packetClasses.containsKey(packetType)){
            throw new IllegalArgumentException("Packet with same name already registered: " + packetType);
        }

        packetClasses.put(packetType, c);
    }

    public Packet createPacketFromJson(String jsonString){
        try{
            JsonElement json = gson.fromJson(jsonString, JsonElement.class);

            String packetType = json.getAsJsonObject().get("type").getAsString();
            json.getAsJsonObject().remove("type");

            Class<?> packetClass = packetClasses.get(packetType);
            if(packetClass == null) throw new RuntimeException("No Packet registered for Packet: " + packetType);
            return (Packet) gson.fromJson(json, packetClass);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
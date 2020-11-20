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

    private static HashMap<Integer, Class<?>> packetClasses = new HashMap<>();
    private final static Gson gson = new Gson();
    private static boolean fuJava = collectTypeClasses();

    public static int getTypeFromPacketClass(Class<?> c){
        try {
            return  c.getField("type").getInt(null);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get typeID from " + c.getSimpleName());
        }
    }
    private static boolean collectTypeClasses(){
        System.out.println("Start Collecting classes");
        Reflections r = new Reflections("de.united.azubiware.Packets");
        Set<Class<? extends IPacket>> classes = r.getSubTypesOf(IPacket.class);
        System.out.println("Got Classes! " + classes.size());
        classes.forEach(new Consumer<Class<? extends IPacket>>() {
            @Override
            public void accept(Class<? extends IPacket> c) {
                System.out.println("Calling Consumer with: " + c.getClass());
                int packetTypeID = getTypeFromPacketClass(c);
                if(packetClasses.containsKey(packetTypeID))
                    throw new RuntimeException("Multiple Packets with same TypeID: " + c.getSimpleName());
                packetClasses.put(packetTypeID, c);
                System.out.println("PacketParser: register packet " + c.getSimpleName());
            }
        });
        return true;
    }

    public static IPacket createPacketFromJson(String jsonString){
        try{
            JsonElement json = gson.fromJson(jsonString, JsonElement.class).getAsJsonObject();

            int packetType = json.getAsJsonObject().get("type").getAsInt();
            if(!packetClasses.containsKey(packetType)) {
                System.out.println("PacketParserError: No PacketClass for type: " + packetType);
                return null;
            };

            Class<?> packetClass = packetClasses.get(packetType);
            return (IPacket) gson.fromJson(json, packetClass);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}

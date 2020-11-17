package de.united.azubiware.Packets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketParser {

    private final static Gson gson = new Gson();

    public static IPacket createPacketFromString(String packetString) throws Exception{
        JsonObject jsonPacket = gson.fromJson(packetString, JsonElement.class).getAsJsonObject();
        int packetId = jsonPacket.getAsJsonObject().get("id").getAsInt();

        switch (packetId){
            case IQueueStartPacket.typeID:
                return createQueuePacket(jsonPacket);
        }

        throw new RuntimeException("Couldn't create Packet with given ID.");
    }

    private static IQueueStartPacket createQueuePacket(JsonObject object){
        return null;
    }

}

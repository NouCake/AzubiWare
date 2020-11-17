package de.united.azubiware;

import de.united.azubiware.Packets.Handler.PacketParser;
import de.united.azubiware.Packets.LoginPacket;

public class Main{

    public static void main(String[] args) {
        LoginPacket packet = null;
        try {
            packet = (LoginPacket) PacketParser.createPacketFromJson("{\"type\": 3, \"username\":\"karl\"}");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println(packet.getUsername());

    }

}
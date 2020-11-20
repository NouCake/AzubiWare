package de.united.azubiware;

import com.google.gson.Gson;
import de.united.azubiware.Lobby.LobbyServer;
import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.Matches.TTT.TTTMatch;
import de.united.azubiware.Packets.Handler.PacketParser;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.User.SimpleUser;

import java.util.Arrays;
import java.util.UUID;

public class Main{

    public static void main(String[] args) {
        new LobbyServer();
    }

    private static void testMatchInfoPacket(){
        UUID u1 = UUID.randomUUID();
        IMatch m = new TTTMatch(100, new SimpleUser(u1, "Bob"), new SimpleUser(UUID.randomUUID(), "Bebb"));
        String packetString = m.getMatchInfoPacket(u1).toJsonString();
        System.out.println(packetString);
        IPacket p = PacketParser.createPacketFromJson(packetString);
        System.out.println(p.toJsonString());
    }


}
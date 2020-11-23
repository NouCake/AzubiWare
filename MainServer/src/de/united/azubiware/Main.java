package de.united.azubiware;

import de.united.azubiware.Lobby.LobbyPacketHandler;
import de.united.azubiware.Lobby.LobbyServer;
import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.Games.TTT.TTTMatch;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.Handler.PacketParser;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.User.SimpleUser;

import java.util.UUID;

public class Main{

    public static void main(String[] args) {
        new LobbyServer();

    }

    private static void testMatchInfoPacket(){

    }


}
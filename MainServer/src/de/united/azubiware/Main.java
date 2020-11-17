package de.united.azubiware;

import de.united.azubiware.Lobby.LobbyServer;
import de.united.azubiware.Packets.Handler.PacketParser;

public class Main{

    public static void main(String[] args) {
        new PacketParser();
        new LobbyServer();
    }

}
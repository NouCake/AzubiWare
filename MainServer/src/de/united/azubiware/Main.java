package de.united.azubiware;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.united.azubiware.Lobby.LobbyServer;
import de.united.azubiware.Packets.Handler.PacketParser;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.MatchConnectionInfoPacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.User.User;

import java.util.UUID;

public class Main{

    public static void main(String[] args) {
        new PacketParser();
        new LobbyServer();
    }


}
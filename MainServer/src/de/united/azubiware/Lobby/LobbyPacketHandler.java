package de.united.azubiware.Lobby;

import de.united.azubiware.Lobby.ILobby;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.QueueStartPacket;
import de.united.azubiware.Packets.QueueStopPacket;
import de.united.azubiware.User.IUser;

public class LobbyPacketHandler extends APacketHandler {

    private ILobby lobby;

    public LobbyPacketHandler(ILobby lobby) {
        this.lobby = lobby;
    }

    public void onQueueStartPacket(IUser user, QueueStartPacket packet){
        System.out.println("Queue Start");
        lobby.startQueueing(user);
    }

    public void onQueueStopPacket(IUser user, QueueStopPacket packet){
        System.out.println("Queue Stop");
        lobby.stopQueueing(user);
    }
}

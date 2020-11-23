package de.united.azubiware.Lobby;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.UserConnectionManager;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.Handler.QueueResponsePacket;
import de.united.azubiware.Packets.QueuePollPacket;
import de.united.azubiware.Packets.QueueStartPacket;
import de.united.azubiware.Packets.QueueStopPacket;
import de.united.azubiware.User.IUserConnection;

public class LobbyPacketHandler extends APacketHandler {

    private final ILobby lobby;
    private final UserConnectionManager connectionManager;

    public LobbyPacketHandler(ILobby lobby, UserConnectionManager connectionManager) {
        this.lobby = lobby;
        this.connectionManager = connectionManager;
    }

    public void onQueueStartPacket(IConnection connection, QueueStartPacket packet){
        System.out.println("Queue Start");
        lobby.startQueueing(connectionManager.getUserFromConnection(connection), packet.getMatchType());
    }

    public void onQueueStopPacket(IConnection connection, QueueStopPacket packet){
        System.out.println("Queue Stop");
        lobby.stopQueueing(connectionManager.getUserFromConnection(connection));
    }

    public void onQueuePollPacket(IConnection c, QueuePollPacket packet){
        IUserConnection user = connectionManager.getUserFromConnection(c);
        if(user == null) return;
        user.send(new QueueResponsePacket(packet.getMatchType(), lobby.getUsersInQueue(packet.getMatchType())));
    }

}

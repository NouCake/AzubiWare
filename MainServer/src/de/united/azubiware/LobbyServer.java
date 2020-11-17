package de.united.azubiware;

import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.UserConnectionListener;
import de.united.azubiware.Connection.WebSocket.IUserListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.IQueueStartPacket;
import de.united.azubiware.Packets.IUserPacketHandler;

import java.util.LinkedList;
import java.util.List;

public class LobbyServer implements IUserListener, ILobby {
    private IConnectionManager manager;
    private List<IUser> queue;
    private IUserPacketHandler packetHandler;

    public LobbyServer(){
        queue = new LinkedList<>();
        manager = new WebSocketConnectionManager();
        manager.setConnectionListener(new UserConnectionListener(this));
    }

    private void tryMatchmaking(){

    }

    @Override
    public void onPacket(IUser user, IPacket packet) {
        packetHandler.onPacket(user, packet);
    }

    @Override
    public void onLogin(IUser user) {
    }

    @Override
    public void onLogout(IUser user) {

    }

    @Override
    public void startQueueing(IUser user, IQueueStartPacket packet) {
        queue.add(user);
        tryMatchmaking();
    }

    @Override
    public void stopQueueing(IUser user) {
        queue.remove(user);
    }
}

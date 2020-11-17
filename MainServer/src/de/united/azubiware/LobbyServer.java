package de.united.azubiware;

import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.UserConnectionListener;
import de.united.azubiware.Connection.WebSocket.IUserListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.Matches.TTTMatch;
import de.united.azubiware.Packets.Handler.IPacket;
import de.united.azubiware.Packets.Handler.IUserPacketHandler;
import de.united.azubiware.Packets.MatchInfoPacket;

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
        synchronized (queue){
            if(queue.size() < 2) return;
            startMatch(new TTTMatch(queue.get(0), queue.get(1)));
        }
    }

    void startMatch(IMatch match){
        MatchInfoPacket packet = match.getMatchInfoPacket();
        for(IUser user : match.getUserList()){
            stopQueueing(user);
            user.getConnection().send(packet);
        }
    }

    @Override
    public void onPacket(IUser user, IPacket packet) {
        packetHandler.onPacket(user, packet);
    }

    @Override
    public void onLogin(IUser user) {
        //Send Welcome
    }

    @Override
    public void onLogout(IUser user) {
        stopQueueing(user);
    }

    @Override
    public void startQueueing(IUser user) {
        synchronized (queue){
            queue.add(user);
        }
        tryMatchmaking();
    }

    @Override
    public void stopQueueing(IUser user) {
        synchronized (queue){
            queue.remove(user);
        }
    }

}

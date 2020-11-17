package de.united.azubiware.Lobby;

import de.united.azubiware.Connection.IConnectionListener;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.UserConnectionListener;
import de.united.azubiware.Connection.WebSocket.IUserListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.Matches.TTTMatch;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.Handler.IUserPacketHandler;
import de.united.azubiware.Packets.MatchInfoPacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.User.IUserDatabase;
import de.united.azubiware.User.SimpleUserDatabase;

import java.util.LinkedList;
import java.util.List;

public class LobbyServer implements ILobby, IUserListener {

    private List<IUser> queue;

    private final IUserPacketHandler packetHandler;
    private final IUserDatabase userDB;
    private final IConnectionManager connectionManager;

    public LobbyServer(){
        queue = new LinkedList<>();

        packetHandler = new LobbyPacketHandler(this);
        userDB = new SimpleUserDatabase();

        IConnectionListener connectionListener = new UserConnectionListener(this, userDB);

        connectionManager = new WebSocketConnectionManager();
        connectionManager.setConnectionListener(connectionListener);
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
            user.send(packet);
        }
    }

    @Override
    public void onPacket(IUser user, IPacket packet) {
        System.out.println("Got Packet " + packet.getClass().getSimpleName());
        packetHandler.onPacket(user, packet);
    }

    @Override
    public void onLogin(IUser user) {
        //Send Welcome
        System.out.println("User logged in " + user.getName());
    }

    @Override
    public void onLogout(IUser user) {
        System.out.println("User logged out " + user.getName());
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
package de.united.azubiware.Lobby;

import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.UserConnectionManager;
import de.united.azubiware.Connection.WebSocket.IUserListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Games.Pong.PongLobbyGame;
import de.united.azubiware.Games.TTT.TTTLobbyGame;
import de.united.azubiware.Games.VG.VGLobbyGame;
import de.united.azubiware.ILobbyGame;
import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.Games.TTT.TTTMatch;
import de.united.azubiware.Games.VG.VGMatch;
import de.united.azubiware.Packets.Handler.IMessageHandler;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.WelcomePacket;
import de.united.azubiware.User.IUserConnection;
import de.united.azubiware.User.IUserDatabase;
import de.united.azubiware.User.SimpleUserDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LobbyServer implements ILobby, IUserListener {

    private final List<ILobbyGame> games;

    private final IMessageHandler packetHandler;
    private final IUserDatabase userDB;
    private final IConnectionManager connectionManager;

    public LobbyServer(){

        games = new LinkedList<>();
        games.add(new TTTLobbyGame());
        games.add(new VGLobbyGame());
        games.add(new PongLobbyGame());

        userDB = new SimpleUserDatabase();
        UserConnectionManager connectionListener = new UserConnectionManager(this, userDB);
        packetHandler = new LobbyPacketHandler(this, connectionListener);

        connectionManager = new WebSocketConnectionManager(12000);
        connectionManager.setConnectionListener(connectionListener);

        connectionManager.start();
    }

    private int getOnlineCount(){
        int count = 0;

        for(ILobbyGame g : games){
            count += g.getQueueCount();
        }
        return count;
    }

    private ILobbyGame getGameByMatchType(int matchType){
        for(ILobbyGame g : games){
            if(g.getMatchType() == matchType) return g;
        }
        System.out.println("No Valid MatchType :C" + matchType);
        return null;
    }
    @Override
    public void onMessage(IUserConnection user, String message) {
        packetHandler.onMessage(user.getConnection(), message);
    }
    @Override
    public void onLogin(IUserConnection user) {
        System.out.println("User logged in " + user.getName());
        user.send(new WelcomePacket(user.getId(), user.getName()));
    }
    @Override
    public void onLogout(IUserConnection user) {
        System.out.println("User logged out " + user.getName());
        stopQueueing(user);
    }
    @Override
    public void startQueueing(IUserConnection user, int matchType) {
        ILobbyGame g = getGameByMatchType(matchType);
        if(g == null) return;
        g.addToQueue(user);
    }
    @Override
    public void stopQueueing(IUserConnection ...users) {
        games.forEach(g -> g.removeFromQueue(users));
    }
    @Override
    public int getUsersInQueue(int matchType) {
        //System.out.println(queue + " | " + queue.size());
        ILobbyGame g = getGameByMatchType(matchType);
        if(g == null) return 0;
        return g.getQueueCount();
    }

}

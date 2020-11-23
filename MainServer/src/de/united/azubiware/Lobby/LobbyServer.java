package de.united.azubiware.Lobby;

import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.UserConnectionManager;
import de.united.azubiware.Connection.WebSocket.IUserListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.ILobbyGame;
import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.Games.TTT.TTTMatch;
import de.united.azubiware.Games.TTT.VG.VGMatch;
import de.united.azubiware.Packets.Handler.IPacketHandler;
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

    private final IPacketHandler packetHandler;
    private final IUserDatabase userDB;
    private final IConnectionManager connectionManager;
    private final HashMap<Integer, Class<? extends IMatch>> matchClasses;

    public LobbyServer(){
        matchClasses = new HashMap<>();
        matchClasses.put(TTTMatch.MATCH_TYPE, TTTMatch.class);
        matchClasses.put(VGMatch.MATCH_TYPE, VGMatch.class);

        games = new LinkedList<>();

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
        return null;
    }
    @Override
    public void onPacket(IUserConnection user, IPacket packet) {
        packetHandler.onPacket(user.getConnection(), packet);
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
        getGameByMatchType(matchType).addToQueue(user);
    }
    @Override
    public void stopQueueing(IUserConnection ...users) {
        games.forEach(g -> g.removeFromQueue(users));
    }
    @Override
    public int getUsersInQueue(int matchType) {
        //System.out.println(queue + " | " + queue.size());
        return getGameByMatchType(matchType).getQueueCount();
    }

}

package de.united.azubiware.Lobby;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.PortManager;
import de.united.azubiware.Connection.UserConnectionManager;
import de.united.azubiware.Connection.WebSocket.IUserListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.Matches.IMatchListener;
import de.united.azubiware.Matches.TTT.TTTMatch;
import de.united.azubiware.Matches.VierGewinnt.VGMatch;
import de.united.azubiware.Packets.Handler.IPacketHandler;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.WelcomePacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.User.IUserConnection;
import de.united.azubiware.User.IUserDatabase;
import de.united.azubiware.User.SimpleUserDatabase;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LobbyServer implements ILobby, IUserListener {

    private final List<IUserConnection> tttQueue;
    private final List<IUserConnection> vgQueue;

    private final IPacketHandler packetHandler;
    private final IUserDatabase userDB;
    private final IConnectionManager connectionManager;
    private final HashMap<Integer, Class<? extends IMatch>> matchClasses;

    public LobbyServer(){
        matchClasses = new HashMap<>();
        matchClasses.put(TTTMatch.MATCH_TYPE, TTTMatch.class);
        matchClasses.put(VGMatch.MATCH_TYPE, VGMatch.class);

        tttQueue = new LinkedList<>();
        vgQueue = new LinkedList<>();

        userDB = new SimpleUserDatabase();
        UserConnectionManager connectionListener = new UserConnectionManager(this, userDB);
        packetHandler = new LobbyPacketHandler(this, connectionListener);

        connectionManager = new WebSocketConnectionManager(12000);
        connectionManager.setConnectionListener(connectionListener);

        connectionManager.start();
    }

    private void tryMatchmaking(){

        List<IUserConnection> q = getQueueForMatch(TTTMatch.MATCH_TYPE);
        synchronized (q){
            if(q.size() < 2) return;
            startMatch(TTTMatch.MATCH_TYPE, q.get(0), q.get(1));
        }

         q = getQueueForMatch(VGMatch.MATCH_TYPE);
        synchronized (q){
            if(q.size() < 2) return;
            startMatch(VGMatch.MATCH_TYPE, q.get(0), q.get(1));
        }
    }
    private void startMatch(int matchType, IUserConnection...users){
        if(!matchClasses.containsKey(matchType)) throw new RuntimeException("Unsupported Match");
        Class<? extends IMatch> matchClass = matchClasses.get(matchType);

        if(!isUserCountValid(matchClass, users.length)){
            System.out.println("Bad User Count!");
            return;
        }

        IMatch match = createMatch(matchType, users);
        if(match != null){
            stopQueueing(users);
        }

        for(IUserConnection connection : users){
            connection.send(match.getMatchInfoPacket(connection.getId()));
        }
        match.start();
    }
    private boolean isUserCountValid(Class<? extends IMatch> c, int usercount){
        try {
            Method m = c.getMethod("isUserCountValid", Integer.class);
            boolean usersValid = (Boolean)m.invoke(null, usercount);
            return usersValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private IMatch createMatch(int matchType, IUser...users) {
        int port;

        try {
            port = PortManager.ports.getFreePort();
        } catch (PortManager.NoFreePortException e) {
            System.out.println("Couldn't start Match: No Free Port");
            return null;
        }

        IMatch match;

        if(matchType == TTTMatch.MATCH_TYPE){
            match = new TTTMatch(port, users[0], users[1]);
        } else if(matchType == VGMatch.MATCH_TYPE){
            match = new VGMatch(port, users[0], users[1]);
        } else {
            throw new RuntimeException("Programmer was very lazy :c");
        }

        match.setMatchListener(new IMatchListener() {
            @Override
            public void onMatchFinished() {
            }

            @Override
            public void onMatchTimedOut() {
            }

            @Override
            public void onMatchClose(){
                PortManager.ports.freePort(port);
            }
        });
        return match;
    }

    private List<IUserConnection> getQueueForMatch(int matchType){
        if(matchType == TTTMatch.MATCH_TYPE) return tttQueue;
        else if(matchType == VGMatch.MATCH_TYPE) return vgQueue;
        return null;
    }

    @Override
    public void onPacket(IUserConnection user, IPacket packet) {
        System.out.println("Got Packet " + packet.getClass().getSimpleName());
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
        List<IUserConnection> q = getQueueForMatch(matchType);
        synchronized (q){
            q.add(user);
        }
        tryMatchmaking();
    }
    @Override
    public void stopQueueing(IUserConnection ...user) {
        synchronized (tttQueue){
            tttQueue.removeAll(Arrays.asList(user.clone()));
        }
        synchronized (vgQueue){
            vgQueue.removeAll(Arrays.asList(user.clone()));
        }
    }
    @Override
    public int getUsersInQueue(int matchType) {
        //System.out.println(queue + " | " + queue.size());
        return getQueueForMatch(matchType).size();
    }

}

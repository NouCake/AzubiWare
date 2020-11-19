package de.united.azubiware.Matches;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.PacketListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.IPacketHandler;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.MatchConnectionInfoPacket;
import de.united.azubiware.Packets.MatchReadyPacket;
import de.united.azubiware.User.IUser;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AMatch implements IMatch {

    private final int TIMEOUT_IN_SEC = 30;
    private final int MATCH_ID;

    private final MatchUser[] users;

    private IMatchListener listener;
    private final IConnectionManager server;
    private final PacketListener packetListener;

    public AMatch(int matchType, int port, IUser ...userlist) {
        this.MATCH_ID = matchType;

        this.packetListener = new PacketListener(new MatchPacketHandler(this)){
            @Override
            public void onClosed(IConnection connection) {
                super.onClosed(connection);
                MatchUser user = getPlayerFromConnection(connection);
                if(user != null){
                    onMatchOver();
                }
            }
        };

        AtomicInteger playerIndex = new AtomicInteger();
        users = Arrays.stream(userlist).map(user -> new MatchUser(user, playerIndex.incrementAndGet())).toArray(MatchUser[]::new);

        server = new WebSocketConnectionManager(port);
        server.setConnectionListener(packetListener);
        server.start();
    }

    protected void addPacketHandler(IPacketHandler packetHandler){
        this.packetListener.addPacketHandler(packetHandler);
    }

    private MatchUser getPlayerFromMatchToken(UUID userMatchToken){
        for(MatchUser user : users){
            if(user.getMatchToken().equals(userMatchToken)) return user;
        }
        return null;
    }
    private MatchUser getPlayerFromUUID(UUID uuid){
        for(MatchUser user : users){
            if(user.getId().equals(uuid)) return user;
        }
        return null;
    }
    public MatchUser getPlayerFromConnection(IConnection connection){
        for(MatchUser user : users){
            if(user.getConnection().equals(connection)) return user;
        }
        return null;
    }
    public MatchUser getPlayerFromIndex(int playerIndex){
        for(MatchUser user : users){
            if(user.getPlayerIndex() == playerIndex) return user;
        }
        return null;
    }

    private void checkIfAllConnected(){
        for(MatchUser user : users){
            if(!user.isConnected()) return;
        }

        sendAllUsers(new MatchReadyPacket());
        onAllUserConnected();
    }
    private void onMatchTimedOut(){
        if(listener != null) listener.onMatchTimedOut();
        onMatchOver();
    }

    protected void onMatchOver(){
        if(listener != null) listener.onMatchFinished();
        server.stop();
    }
    protected void onUserConnected(IConnection connection, UUID userMatchToken){
        System.out.println("MatchUser Connected");
        MatchUser user = getPlayerFromMatchToken(userMatchToken);
        if(user == null) {
            connection.send(new ErrorResponsePacket("Token probably Wrong!"));
            return;
        }
        if(user.isConnected()){
            user.send(new ErrorResponsePacket("You are already connected to the Match!"));
            return;
        }
        user.setConnection(connection);
        System.out.println(user.getName());

        checkIfAllConnected();
    }
    protected abstract void onAllUserConnected();

    protected void sendAllUsers(IPacket packet){
        for(MatchUser user : users){
            if(user.isConnected())
                user.send(packet);
        }
    }

    @Override
    public int getMatchType() {
        return MATCH_ID;
    }
    @Override
    public IUser[] getUserList() {
        return users;
    }
    @Override
    public void setMatchListener(IMatchListener listener) {
        this.listener = listener;
    }
    @Override
    public void start() {
        new Thread(() -> {
            try {
                Thread.sleep(TIMEOUT_IN_SEC * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (MatchUser user : users) {
                if (!user.isConnected()) {
                    onMatchTimedOut();
                    return;
                }
            }
        }).start();
    }
    @Override
    public MatchConnectionInfoPacket getMatchInfoPacket(UUID userId) {
        MatchUser user = getPlayerFromUUID(userId);
        if(user == null) throw new RuntimeException("This user wasn't there when match was started!");

        IUser[] oponents = Arrays.stream(users)
                .filter(mu -> mu.getId().equals(user.getId()))
                .toArray(IUser[]::new);

        return new MatchConnectionInfoPacket(getMatchType(), server.getConnectionAdress(), user.getMatchToken(), null);
    }

}

package de.united.azubiware.Matches;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.PacketListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Packets.*;
import de.united.azubiware.Packets.Handler.IMessageHandler;
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

    private int playerWon = 0;
    private boolean matchRunning = true;

    public AMatch(int matchType, int port, IUser ...userlist) {
        this.MATCH_ID = matchType;

        this.packetListener = new PacketListener(new MatchPacketHandler(this)){
            @Override
            public void onClosed(IConnection connection) {
                super.onClosed(connection);
                if(!matchRunning) return;
                MatchUser user = getPlayerFromConnection(connection);
                if(user != null){
                    onMatchOver(MatchOverPacket.REASONS.ABORTED.ordinal());
                }
            }

            @Override
            public void afterShutdown() {
                super.afterShutdown();
                if(listener != null) listener.onMatchClose();
            }
        };

        AtomicInteger playerIndex = new AtomicInteger();
        users = Arrays.stream(userlist).map(user -> new MatchUser(user, playerIndex.incrementAndGet())).toArray(MatchUser[]::new);

        server = new WebSocketConnectionManager(port);
        server.setConnectionListener(packetListener);
        server.start();
    }

    protected void addPacketHandler(IMessageHandler packetHandler){
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
            if(user.isConnected() && user.getConnection().equals(connection)) return user;
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
        onMatchReady();
    }
    private void onMatchTimedOut(){
        if(listener != null) listener.onMatchTimedOut();
        onMatchOver(MatchOverPacket.REASONS.ABORTED.ordinal());
    }
    private void onMatchReady(){
        sendAllUsers(new MatchReadyPacket());
        onAllUserConnected();
    }
    private void sendMatchOverPackets(int r){
        for(MatchUser user : users){
            int reason = r;
            if(r == MatchOverPacket.REASONS.GAME_DONE.ordinal()){
                if(playerWon == 0) {
                    reason = MatchOverPacket.REASONS.DRAW.ordinal();
                } else {
                    reason = playerWon == user.getPlayerIndex() ? MatchOverPacket.REASONS.YOU_WON.ordinal() : MatchOverPacket.REASONS.YOU_LOST.ordinal();
                }
            }
            if(user.isConnected()){
                user.send(new MatchOverPacket(reason));
            }
        }
    }

    protected void onMatchOver(int reason){
        matchRunning = false;
        if(listener != null) listener.onMatchFinished();
        sendMatchOverPackets(reason);
        server.stop();
    }
    protected void onUserConnected(IConnection connection, UUID userMatchToken){
        System.out.println("MatchUser Login Try");
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
    protected void sendAllUsers(IPacket packet){
        for(MatchUser user : users){
            if(user.isConnected())
                user.send(packet);
        }
    }
    protected abstract void onAllUserConnected();


    public void setPlayerWon(int playerIndex){
        if(playerWon != 0){
            throw new RuntimeException("Match is already decided: " + playerWon + " has won!");
        }
        this.playerWon = playerIndex;
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
            System.out.println("Checking for Timeout!");
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
                .filter(mu -> !mu.getId().equals(user.getId()))
                .toArray(IUser[]::new);

        return new MatchConnectionInfoPacket(getMatchType(), server.getConnectionAdress(), user.getMatchToken(), oponents);
    }

}

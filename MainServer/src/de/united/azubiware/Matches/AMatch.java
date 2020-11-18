package de.united.azubiware.Matches;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.PacketListener;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.IPacketHandler;
import de.united.azubiware.Packets.MatchConnectionInfoPacket;
import de.united.azubiware.User.IUser;

import java.util.Arrays;
import java.util.UUID;

public abstract class AMatch implements IMatch {

    private final int TIMEOUT_IN_SEC = 30;
    private final int MATCH_ID;

    private final MatchUser[] users;

    private IMatchListener listener;
    private IConnectionManager server;

    public AMatch(int matchType, int port, IPacketHandler packetHandler, IUser ...userlist) {
        this.MATCH_ID = matchType;

        System.out.println("Match " + getClass().getSimpleName() + " was Started");

        users = Arrays.stream(userlist).map(MatchUser::new).toArray(MatchUser[]::new);

        server = new WebSocketConnectionManager(port);
        server.setConnectionListener(new PacketListener(packetHandler, new MatchPacketHandler(this)));
        server.start();
    }

    private MatchUser getUserFromMatchToken(UUID userMatchToken){
        for(MatchUser user : users){
            if(user.getMatchToken().equals(userMatchToken)) return user;
        }
        return null;
    }

    protected MatchUser getUserFromMatchConnection(IConnection connection){
        for(MatchUser user : users){
            if(user.getConnection().equals(connection)) return user;
        }
        return null;
    }

    private MatchUser getUserFromUUID(UUID uuid){
        for(MatchUser user : users){
            if(user.getId().equals(uuid)) return user;
        }
        return null;
    }

    protected void onUserConnected(IConnection connection, UUID userMatchToken){
        System.out.println("MatchUser Connected");
        MatchUser user = getUserFromMatchToken(userMatchToken);
        if(user == null) {
            connection.send(new ErrorResponsePacket("Token probably Wrong!"));
            return;
        }
        if(user.isConnected()){
            user.send(new ErrorResponsePacket("You are already connected to the Match!"));
            return;
        }
        user.setConnection(connection);
    }

    private void checkIfAllConnected(){
        for(MatchUser user : users){
            if(!user.isConnected()) return;
        }
        //All connected
        onAllUserConnected();
    }

    protected abstract void onAllUserConnected();

    protected void onMatchOver(){
        if(listener != null) listener.onMatchFinished();
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
                    if(listener != null) listener.onMatchTimedOut();
                    return;
                }
            }
        }).start();
    }

    @Override
    public MatchConnectionInfoPacket getMatchInfoPacket(UUID userId) {
        MatchUser user = getUserFromUUID(userId);
        if(user == null) throw new RuntimeException("This user wasn't there when match was started!");
        return new MatchConnectionInfoPacket(getMatchType(), server.getConnectionAdress(), user.getMatchToken());
    }

}

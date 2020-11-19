package de.united.azubiware.Matches;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.User.IUserConnection;

import java.util.UUID;

public class MatchUser implements IUserConnection {

    private final IUser user;
    private final UUID matchToken;
    private IConnection connection;
    private final int playerIndex;

    public MatchUser(IUser user, int playerIndex) {
        this.user = user;
        this.playerIndex = playerIndex;
        matchToken = UUID.randomUUID();

        System.out.println("New MatchUser: " + playerIndex);
    }

    public boolean isConnected(){
        return connection != null;
    }

    public void setConnection(IConnection connection){
        if(this.connection != null)
            throw new RuntimeException("User is already Connected!");
        this.connection = connection;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public UUID getMatchToken() {
        return matchToken;
    }

    @Override
    public UUID getId() {
        return user.getId();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public IConnection getConnection() {
        return connection;
    }

    @Override
    public void send(IPacket packet) {
        connection.send(packet);
    }
}

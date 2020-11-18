package de.united.azubiware.Matches;

import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.WebSocket.WebSocketConnectionManager;
import de.united.azubiware.Packets.MatchConnectionInfoPacket;
import de.united.azubiware.User.IUser;

import java.util.LinkedList;
import java.util.List;

public class TTTMatch implements IMatch {

    private final static int MATCH_ID = 1;

    private IUser u1;
    private IUser u2;
    private final List<IUser> users;

    private IMatchListener listener;
    private IConnectionManager server;

    public TTTMatch(int port, IUser u1, IUser u2) {
        this.u1 = u1;
        this.u2 = u2;

        users = new LinkedList<>();
        users.add(u1);
        users.add(u2);

        server = new WebSocketConnectionManager(port);
    }

    @Override
    public int getMatchType() {
        return MATCH_ID;
    }

    @Override
    public List<IUser> getUserList() {
        return users;
    }

    @Override
    public MatchConnectionInfoPacket getMatchInfoPacket() {
        return new MatchConnectionInfoPacket(0, server.getConnectionAdress());
    }

    @Override
    public void setMatchListener(IMatchListener listener) {
        this.listener = listener;
    }

}

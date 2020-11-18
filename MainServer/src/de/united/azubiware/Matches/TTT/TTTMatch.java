package de.united.azubiware.Matches.TTT;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.User.IUser;

public class TTTMatch extends AMatch {

    private final static int MATCH_TYPE = 1;

    private final TTTPacketHandler packetHandler;

    private final IUser u1;
    private final IUser u2;

    public TTTMatch(int port, IUser u1, IUser u2) {
        this(port, new TTTPacketHandler(), u1, u2);
    }

    private TTTMatch(int port, TTTPacketHandler handler, IUser u1, IUser u2){
        super(MATCH_TYPE, port, handler, u1, u2);
        this.packetHandler = handler;
        this.u1 = u1;
        this.u2 = u2;
        handler.setMatch(this);
    }

    public int getPlayerFromConnection(IConnection connection){
        if(getUserFromMatchConnection(connection).getId().equals(u1.getId()))
            return 1;
        if(getUserFromMatchConnection(connection).getId().equals(u2.getId()))
            return 2;

        throw new RuntimeException("Unknown Connection");
    }

    @Override
    protected void onAllUserConnected() {

    }

}

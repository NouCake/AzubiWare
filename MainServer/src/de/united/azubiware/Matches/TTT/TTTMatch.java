package de.united.azubiware.Matches.TTT;

import de.united.azubiware.Matches.AMatch;
import de.united.azubiware.User.IUser;

public class TTTMatch extends AMatch {

    private final static int MATCH_TYPE = 1;

    private final TTTPacketHandler packetHandler;

    public TTTMatch(int port, IUser u1, IUser u2) {
        this(port, new TTTPacketHandler(), u1, u2);
    }

    private TTTMatch(int port, TTTPacketHandler handler, IUser u1, IUser u2){
        super(MATCH_TYPE, port, handler, u1, u2);
        this.packetHandler = handler;
    }

    @Override
    protected void onAllUserConnected() {

    }

}

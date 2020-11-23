package de.united.azubiware.Games.TTT;

import de.united.azubiware.Packets.IPacket;

public class TTTNextTurnPacket implements IPacket {

    public static final int type = 14;

    private final boolean yourTurn;

    public TTTNextTurnPacket(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }
}

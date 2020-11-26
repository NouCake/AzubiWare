package de.united.azubiware.Games.TTT;

import de.united.azubiware.Packets.IPacket;

public class TTTNextTurnPacket implements IPacket {


    private final boolean yourTurn;

    public TTTNextTurnPacket(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }
}

package de.united.azubiware.Games.SV;

import de.united.azubiware.Packets.IPacket;

public class BattleshipNextTurnPacket implements IPacket {

    private final boolean yourTurn;

    public BattleshipNextTurnPacket(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

}

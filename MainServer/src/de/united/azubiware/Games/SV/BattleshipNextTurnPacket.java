package de.united.azubiware.Games.SV;

public class BattleshipNextTurnPacket {

    private final boolean yourTurn;

    public BattleshipNextTurnPacket(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

}

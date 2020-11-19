package de.united.azubiware.Packets;

public class TTTNextTurnPacket implements IPacket{

    public static final int type = 13;

    private final boolean yourTurn;

    public TTTNextTurnPacket(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }
}

package de.united.azubiware.Games.VG;

import de.united.azubiware.Packets.IPacket;

public class VGNextTurnPacket implements IPacket {

    public static final int type = 18;

    private final boolean yourTurn;

    public VGNextTurnPacket(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }
}

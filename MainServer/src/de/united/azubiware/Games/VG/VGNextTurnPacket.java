package de.united.azubiware.Games.VG;

import de.united.azubiware.Packets.IPacket;

public class VGNextTurnPacket implements IPacket {

    private final boolean yourTurn;

    public VGNextTurnPacket(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }
}

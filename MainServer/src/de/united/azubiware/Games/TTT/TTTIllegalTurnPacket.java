package de.united.azubiware.Games.TTT;

import de.united.azubiware.Packets.IPacket;

public class TTTIllegalTurnPacket implements IPacket {

    public static final int type = 15;
    private final String message;

    public TTTIllegalTurnPacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

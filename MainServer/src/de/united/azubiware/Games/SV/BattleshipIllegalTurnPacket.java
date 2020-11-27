package de.united.azubiware.Games.SV;

import de.united.azubiware.Packets.IPacket;

public class BattleshipIllegalTurnPacket implements IPacket {

    private final String message;

    public BattleshipIllegalTurnPacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

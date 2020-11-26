package de.united.azubiware.Games.VG;

import de.united.azubiware.Packets.IPacket;

public class VGTurnHint implements IPacket {
    private final int row;

    public VGTurnHint(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

}

package de.united.azubiware.Games.VG;

import de.united.azubiware.Packets.IPacket;

public class VGPacket implements IPacket {

    private final int fieldX;

    public VGPacket(int fieldX) {
        this.fieldX = fieldX;
    }

    public int getFieldX() {
        return fieldX;
    }
}

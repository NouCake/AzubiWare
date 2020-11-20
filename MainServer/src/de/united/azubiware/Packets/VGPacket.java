package de.united.azubiware.Packets;

public class VGPacket implements IPacket{
    public static final int type = 16;

    private final int fieldX;

    public VGPacket(int fieldX) {
        this.fieldX = fieldX;
    }

    public int getFieldX() {
        return fieldX;
    }
}

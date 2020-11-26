package de.united.azubiware.Games.TTT;

import de.united.azubiware.Packets.IPacket;

public class TTTPacket implements IPacket {


    private final int fieldX;
    private final int fieldY;

    public TTTPacket(int fieldX, int fieldY) {
        this.fieldX = fieldX;
        this.fieldY = fieldY;
    }

    public int getFieldX() {
        return fieldX;
    }

    public int getFieldY() {
        return fieldY;
    }
}

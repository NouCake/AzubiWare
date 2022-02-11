package ttt;

import connection.packet.Packet;

public class TTTPacket implements Packet {

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

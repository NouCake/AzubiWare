package de.united.azubiware.Games.Pong;

import de.united.azubiware.Packets.IPacket;

public class PongPlayerUpdatePacket implements IPacket {

    private final float x;

    public PongPlayerUpdatePacket(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }
}

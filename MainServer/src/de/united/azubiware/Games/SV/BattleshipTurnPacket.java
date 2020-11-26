package de.united.azubiware.Games.SV;

import de.united.azubiware.Packets.IPacket;

public class BattleshipTurnPacket implements IPacket {

    private final int cellX;
    private final int cellY;
    private final boolean hit;


    public BattleshipTurnPacket(int cellX, int cellY, boolean hit) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.hit = hit;
    }

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public boolean isHit() {
        return hit;
    }
}

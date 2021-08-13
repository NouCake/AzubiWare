package de.united.azubiware.Games.SV;

import de.united.azubiware.Packets.IPacket;

public class BattleshipTurnPacket implements IPacket {

    private final int cellX;
    private final int cellY;
    private final boolean hit;
    private final boolean you;

    public BattleshipTurnPacket(int cellX, int cellY, boolean hit, boolean you) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.hit = hit;
        this.you = you;
    }

    public boolean isYou() {
        return you;
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

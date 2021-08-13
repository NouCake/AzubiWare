package de.united.azubiware.Games.SV;

import de.united.azubiware.Packets.IPacket;

public class ShipSetupPacket implements IPacket {

    private final int[][] ships;

    public ShipSetupPacket(int[][] ships) {
        this.ships = ships;
    }

    public int[][] getShips() {
        return ships;
    }
}

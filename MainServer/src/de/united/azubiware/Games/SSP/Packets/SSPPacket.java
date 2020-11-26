package de.united.azubiware.Games.SSP.Packets;

import de.united.azubiware.Packets.IPacket;

public class SSPPacket implements IPacket {

    private int pickType;

    public SSPPacket(int pickType){
        this.pickType = pickType;
    }

    public int getPickType() {
        return pickType;
    }
}

package de.united.azubiware.Games.SSP.Packets;

import de.united.azubiware.Packets.IPacket;

public class SSPRoundStartPacket implements IPacket {

    private int round;

    public SSPRoundStartPacket(int round){
        this.round = round;
    }

    public int getRound() {
        return round;
    }
}

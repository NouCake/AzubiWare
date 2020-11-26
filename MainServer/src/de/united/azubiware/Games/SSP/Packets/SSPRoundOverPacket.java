package de.united.azubiware.Games.SSP.Packets;

import de.united.azubiware.Packets.IPacket;

public class SSPRoundOverPacket implements IPacket {

    private int enemyPick;
    private int result;

    public SSPRoundOverPacket(int enemyPick, int result){
        this.enemyPick = enemyPick;
        this.result = result;
    }

    public int getEnemyPick() {
        return enemyPick;
    }

    public int getResult() {
        return result;
    }
}

package de.united.azubiware.Games.Pong;

import de.united.azubiware.Packets.IPacket;

public class PongScoreUpdatePacket implements IPacket {

    private final int scoreP1;
    private final int scoreP2;

    public PongScoreUpdatePacket(int scoreP1, int scoreP2) {
        this.scoreP1 = scoreP1;
        this.scoreP2 = scoreP2;
    }

    public int getScoreP1() {
        return scoreP1;
    }

    public int getScoreP2() {
        return scoreP2;
    }
}

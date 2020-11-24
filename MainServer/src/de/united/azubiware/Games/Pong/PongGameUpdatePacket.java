package de.united.azubiware.Games.Pong;

import de.united.azubiware.Packets.IPacket;

public class PongGameUpdatePacket implements IPacket {

    private final float enemyX;
    private final float ballX;
    private final float ballY;

    public PongGameUpdatePacket(float enemyX, float ballX, float ballY) {
        this.enemyX = enemyX;
        this.ballX = ballX;
        this.ballY = ballY;
    }

    public float getEnemyX() {
        return enemyX;
    }

    public float getBallX() {
        return ballX;
    }

    public float getBallY() {
        return ballY;
    }
}

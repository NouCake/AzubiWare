package de.united.azubiware.screens.minigames.pong;

import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.connection.match.pong.IPongListener;
import de.united.azubiware.screens.minigames.WaitingScreen;

public class PongListener implements IPongListener {

    private final PongScreen screen;
    private final WaitingScreen waitingScreen;

    public PongListener(PongScreen screen, WaitingScreen waitingScreen) {
        this.screen = screen;
        this.waitingScreen = waitingScreen;
    }

    @Override
    public void onEnemyMoved(float x) {
        screen.updateEnemy(x);
    }

    @Override
    public void onScoreChanged(int p1, int p2) {
        screen.updateScore(p1, p2);
    }

    @Override
    public void onBallMoved(float x, float y) {
        screen.updateBall(x*1.05f - 0.025f, y*1.05f - 0.025f);
    }

    @Override
    public void onMatchReady() {
        waitingScreen.setSwitchToMatch(screen);
    }

    @Override
    public void onMatchOver(int reason) {
        if(reason == MatchOverPacket.REASONS.DRAW.ordinal()){
            screen.getResultOverlay().setResult(0);
        }else if (MatchOverPacket.REASONS.ABORTED.ordinal() == reason || MatchOverPacket.REASONS.YOU_WON.ordinal() == reason) {
            screen.getResultOverlay().setResult(1);
        } else {
            screen.getResultOverlay().setResult(-1);
        }
    }
}

package de.united.azubiware.screens.minigames.vg;

import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.connection.match.IVGListener;
import de.united.azubiware.screens.minigames.WaitingScreen;

public class FourWinsMatchListener implements IVGListener {

    private final FourWinsScreen screen;
    private final WaitingScreen waitingScreen;

    public FourWinsMatchListener(FourWinsScreen screen, WaitingScreen waitingScreen) {
        this.screen = screen;
        this.waitingScreen = waitingScreen;
    }

    @Override
    public void onNextTurn(boolean yourTurn) {
        screen.setTurn(yourTurn);
    }

    @Override
    public void onInvalidTurn() {

    }

    @Override
    public void onEnemyTurn(int row) {
        screen.doEnemyTurn(row);
    }

    @Override
    public void onHint(int row) {
        screen.onHint(row);
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

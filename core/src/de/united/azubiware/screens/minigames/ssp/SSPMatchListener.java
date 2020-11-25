package de.united.azubiware.screens.minigames.ssp;

import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.connection.match.ISSPListener;
import de.united.azubiware.screens.minigames.WaitingScreen;

public class SSPMatchListener implements ISSPListener {

    private WaitingScreen waitingScreen;
    private SSPScreen sspScreen;

    public SSPMatchListener(WaitingScreen waitingScreen, SSPScreen sspScreen){
        this.waitingScreen = waitingScreen;
        this.sspScreen = sspScreen;
    }

    @Override
    public void onRoundStart(int round) {
        if(sspScreen != null)
            sspScreen.startRound(round);
    }

    @Override
    public void onRoundOver() {
    }

    @Override
    public void onRoundResult(int enemyPick, boolean won) {
        if(sspScreen != null)
            sspScreen.showRoundResult(enemyPick, won);
    }

    @Override
    public void onMatchReady() {
        if(waitingScreen != null)
            waitingScreen.setSwitchToMatch(sspScreen);
    }

    @Override
    public void onMatchOver(int reason) {
        if(sspScreen != null) {
            if(reason == MatchOverPacket.REASONS.DRAW.ordinal()){
                sspScreen.getResultOverlay().setResult(0);
            }else if (MatchOverPacket.REASONS.ABORTED.ordinal() == reason || MatchOverPacket.REASONS.YOU_WON.ordinal() == reason) {
                sspScreen.getResultOverlay().setResult(1);
            } else {
                sspScreen.getResultOverlay().setResult(-1);
            }
        }
    }
}

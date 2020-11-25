package de.united.azubiware.screens.minigames.ttt;

import de.united.azubiware.Packets.MatchOverPacket;
import de.united.azubiware.connection.match.ITTTListener;
import de.united.azubiware.screens.minigames.WaitingScreen;

public class TTTMatchListener implements ITTTListener {

    public WaitingScreen waitingScreen;
    public TTTScreen ticTacToeScreen;

    public TTTMatchListener(WaitingScreen waitingScreen, TTTScreen ticTacToeScreen){
        this.waitingScreen = waitingScreen;
        this.ticTacToeScreen = ticTacToeScreen;
    }

    @Override
    public void onNextTurn(boolean yourTurn) {
        if(ticTacToeScreen != null){
            ticTacToeScreen.setTurn(yourTurn);
        }
    }

    @Override
    public void onInvalidTurn() {
        if(ticTacToeScreen != null){

        }
    }

    @Override
    public void onEnemyTurn(int x, int y) {
        if(ticTacToeScreen != null){
            TTTTPostition position = ticTacToeScreen.getTicTacToeField().findPositionByVector(x, y);
            if(position != null){
                ticTacToeScreen.getTicTacToeField().findPositionByVector(x, y).setState(-1);
            }
        }
    }

    @Override
    public void onMatchReady() {
        if(waitingScreen != null)
            waitingScreen.setSwitchToMatch(ticTacToeScreen);
    }

    @Override
    public void onMatchOver(int reason) {
        if(ticTacToeScreen != null) {
            if(reason == MatchOverPacket.REASONS.DRAW.ordinal()){
                ticTacToeScreen.getResultOverlay().setResult(0);
            }else if (MatchOverPacket.REASONS.ABORTED.ordinal() == reason || MatchOverPacket.REASONS.YOU_WON.ordinal() == reason) {
                ticTacToeScreen.getResultOverlay().setResult(1);
            } else {
                ticTacToeScreen.getResultOverlay().setResult(-1);
            }
        }
    }
}

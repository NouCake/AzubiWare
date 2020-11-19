package de.united.azubiware.screens.minigames.ttt;

import de.united.azubiware.connection.match.ITTTListener;
import de.united.azubiware.screens.minigames.WaitingScreen;

public class TTTMatchListener implements ITTTListener {

    public WaitingScreen waitingScreen;
    public TicTacToeScreen ticTacToeScreen;

    public TTTMatchListener(WaitingScreen waitingScreen){
        this.waitingScreen = waitingScreen;
    }

    public void switchToGameScreen(TicTacToeScreen ticTacToeScreen){
        this.waitingScreen = null;
        this.ticTacToeScreen = ticTacToeScreen;
    }

    @Override
    public void onNextTurn(boolean yourTurn) {
        if(ticTacToeScreen != null){
            ticTacToeScreen.setYourTurn(yourTurn);
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
            ticTacToeScreen.getTicTacToeField().findPositionByVector(new Integer[]{x, y}).setState(-1);
        }
    }

    @Override
    public void onMatchReady() {
        if(waitingScreen != null)
            waitingScreen.setSwitchToMatch(true);
    }
}

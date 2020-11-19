package de.united.azubiware.screens.minigames.ttt;

import de.united.azubiware.connection.match.ITTTListener;
import de.united.azubiware.screens.minigames.WaitingScreen;

public class TTTMatchListener implements ITTTListener {

    public WaitingScreen waitingScreen;

    public TTTMatchListener(WaitingScreen waitingScreen){
        this.waitingScreen = waitingScreen;
    }

    @Override
    public void onNextTurn(boolean yourTurn) {

    }

    @Override
    public void onMatchReady() {
        waitingScreen.setSwitchToMatch(true);
    }
}

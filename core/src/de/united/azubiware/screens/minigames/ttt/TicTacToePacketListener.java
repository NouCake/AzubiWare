package de.united.azubiware.screens.minigames.ttt;

import de.united.azubiware.Connection.IClientListener;
import de.united.azubiware.User.IUser;

public class TicTacToePacketListener implements IClientListener {

    private TicTacToeScreen ticTacToeScreen;

    public TicTacToePacketListener(TicTacToeScreen ticTacToeScreen){
        this.ticTacToeScreen = ticTacToeScreen;
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onError(String messsage) {

    }

    @Override
    public void onWelcome(IUser user) {

    }

    @Override
    public void onQueueUpdate(int matchType, int usersInQueue) {

    }

    @Override
    public void onMatchStart(int matchType, IUser... oponents) {

    }
}

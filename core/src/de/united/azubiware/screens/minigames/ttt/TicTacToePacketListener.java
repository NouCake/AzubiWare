package de.united.azubiware.screens.minigames.ttt;

import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.client.IClientListener;

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
    public void onMatchFound(int matchType, IUser... oponents) {

    }

}

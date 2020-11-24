package de.united.azubiware.screens.menu;

import de.united.azubiware.Games.TTT.TTTMatch;
import de.united.azubiware.Games.VG.VGMatch;
import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.client.IClientListener;

public class MenuPacketListener implements IClientListener {

    private MainMenuScreen menuScreen;

    public MenuPacketListener(MainMenuScreen menuScreen) {
        this.menuScreen = menuScreen;
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
        if (matchType == menuScreen.paginator.getCurrentMatchType())
            menuScreen.setWaiting(usersInQueue);
    }

    @Override
    public void onMatchFound(int matchType, IUser... opponents) {
        if (matchType == menuScreen.paginator.getCurrentMatchType()) {
            if (menuScreen.game.getGameManager().isValidMatchType(matchType)) {
                menuScreen.startMatch(matchType, opponents);
            }
        }
    }

}


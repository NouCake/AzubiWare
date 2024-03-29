package de.united.azubiware.screens.menu;

import de.united.azubiware.User.IUser;
import de.united.azubiware.utility.adapters.ClientListenerAdapter;

public class MenuPacketListener extends ClientListenerAdapter {

    private MainMenuScreen menuScreen;

    public MenuPacketListener(MainMenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    @Override
    public void onConnected() {
    }

    @Override
    public void onClosed() {

    }

    @Override
    public void onError(String messsage) {

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
            } else {
                System.out.println("Got Match with invalid MatchType");
            }
        } else {
            System.out.println("What?!");
        }
    }

}

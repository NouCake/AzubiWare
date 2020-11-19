package de.united.azubiware.screens.menu;

import de.united.azubiware.Connection.IClientListener;
import de.united.azubiware.Matches.TTT.TTTMatch;
import de.united.azubiware.User.IUser;

public class MenuPacketListener implements IClientListener {

    private MainMenuScreen menuScreen;

    public MenuPacketListener(MainMenuScreen menuScreen){
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
        if(matchType == menuScreen.paginator.getCurrentMatchType())
            menuScreen.setWaiting(usersInQueue);
    }

    @Override
    public void onMatchFound(int matchType, IUser... oponents) {
        if(matchType == menuScreen.paginator.getCurrentMatchType()){
            if(matchType == TTTMatch.MATCH_TYPE){
                menuScreen.startMatch();
            }
        }
    }
}

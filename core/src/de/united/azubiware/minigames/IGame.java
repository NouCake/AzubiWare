package de.united.azubiware.minigames;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.screens.minigames.WaitingScreen;

public interface IGame {

    int getMatchType();

    Texture getWaitingScreenBackground();

    Texture getSplash();

    IMatchListener createMatchListener(WaitingScreen waitingScreen, AzubiWareGame game, IUser[] opponents);

}

package de.united.azubiware.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.SSP.SSPMatch;
import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.minigames.interfaces.IGame;
import de.united.azubiware.screens.minigames.WaitingScreen;
import de.united.azubiware.screens.minigames.ssp.SSPMatchListener;
import de.united.azubiware.screens.minigames.ssp.SSPScreen;

public class SSP implements IGame {
    @Override
    public int getMatchType() {
        return SSPMatch.MATCH_TYPE;
    }

    @Override
    public Texture getWaitingScreenBackground() {
        return new Texture(Gdx.files.internal("backgrounds/backgroundDesert.png"));
    }

    @Override
    public Texture getSplash() {
        return new Texture(Gdx.files.internal("games/ssp/splash.png"));
    }

    @Override
    public IMatchListener createMatchListener(WaitingScreen waitingScreen, AzubiWareGame game, IUser[] opponents) {
        return new SSPMatchListener(waitingScreen, new SSPScreen(game, opponents[0]));
    }
}

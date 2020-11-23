package de.united.azubiware.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.TTT.TTTMatch;
import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.minigames.interfaces.IGame;
import de.united.azubiware.screens.minigames.WaitingScreen;
import de.united.azubiware.screens.minigames.ttt.TTTMatchListener;
import de.united.azubiware.screens.minigames.ttt.TicTacToeScreen;

public class TicTacToe implements IGame {

    @Override
    public int getMatchType() {
        return TTTMatch.MATCH_TYPE;
    }

    @Override
    public Texture getWaitingScreenBackground() {
        return new Texture(Gdx.files.internal("backgrounds/backgroundCastles.png"));
    }

    @Override
    public Texture getSplash() {
        return new Texture(Gdx.files.internal("games/ttt/splash.png"));
    }

    @Override
    public IMatchListener createMatchListener(WaitingScreen waitingScreen, AzubiWareGame game, IUser[] opponents) {
        return new TTTMatchListener(waitingScreen, new TicTacToeScreen(game, opponents));
    }

}

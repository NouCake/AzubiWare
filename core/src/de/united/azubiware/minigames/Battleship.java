package de.united.azubiware.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.SV.BattleshipMatch;
import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.minigames.interfaces.IGame;
import de.united.azubiware.screens.minigames.WaitingScreen;
import de.united.azubiware.screens.minigames.vg.FourWinsMatchListener;
import de.united.azubiware.screens.minigames.vg.FourWinsScreen;

public class Battleship implements IGame {
    @Override
    public int getMatchType() {
        return BattleshipMatch.MATCH_TYPE;
    }

    @Override
    public Texture getWaitingScreenBackground() {
        return new Texture(Gdx.files.internal("backgrounds/backgroundForest.png"));
    }

    @Override
    public Texture getSplash() {
        return new Texture(Gdx.files.internal("games/sv/splash.png"));
    }

    @Override
    public IMatchListener createMatchListener(WaitingScreen waitingScreen, AzubiWareGame game, IUser[] opponents) {
        return new FourWinsMatchListener(new FourWinsScreen(game, opponents[0]), waitingScreen);
    }

package de.united.azubiware.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.VG.VGMatch;
import de.united.azubiware.User.IUser;
import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.screens.minigames.WaitingScreen;
import de.united.azubiware.screens.minigames.vg.FourWinsMatchListener;
import de.united.azubiware.screens.minigames.vg.FourWinsScreen;

public class FourWins implements IGame{
    @Override
    public int getMatchType() {
        return VGMatch.MATCH_TYPE;
    }

    @Override
    public Texture getWaitingScreenBackground() {
        return new Texture(Gdx.files.internal("backgrounds/backgroundForest.png"));
    }

    @Override
    public Texture getSplash() {
        return new Texture(Gdx.files.internal("games/vg/splash.png"));
    }

    @Override
    public IMatchListener createMatchListener(WaitingScreen waitingScreen, AzubiWareGame game, IUser[] opponents) {
        return new FourWinsMatchListener(new FourWinsScreen(game, opponents[0]), waitingScreen);
    }
}

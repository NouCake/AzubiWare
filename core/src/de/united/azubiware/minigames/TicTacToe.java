package de.united.azubiware.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Matches.TTT.TTTMatch;
import de.united.azubiware.screens.minigames.ttt.TicTacToeScreen;

public class TicTacToe implements IGame{

    @Override
    public int getMatchType() {
        return TTTMatch.MATCH_TYPE;
    }

    @Override
    public Texture getBackground() {
        return new Texture(Gdx.files.internal("backgrounds/backgroundCastles.png"));
    }

    @Override
    public Texture getSplash() {
        return new Texture(Gdx.files.internal("games/ttt/splash.png"));
    }

    @Override
    public Screen createStage(AzubiWareGame game) {
        return new TicTacToeScreen(game);
    }
}

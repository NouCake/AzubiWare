package de.united.azubiware.minigames;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import de.united.azubiware.AzubiWareGame;

public interface IGame {

    int getMatchType();

    Texture getBackground();

    Texture getSplash();

    Screen createStage(AzubiWareGame game);


}

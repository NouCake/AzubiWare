package de.united.azubiware.utility;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

public interface IClickListener {

    boolean touchDown(InputEvent event, float x, float y, int pointer, int button);
    boolean keyDown(InputEvent event, int keycode);
    boolean mouseMoved(InputEvent event, float x, float y);

}

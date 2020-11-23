package de.united.azubiware.utility;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ClickListenerAdapter extends ClickListener {

    private final IClickListener listener;

    public ClickListenerAdapter(IClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return listener.touchDown(event, x, y, pointer, button) || super.touchDown(event, x, y, pointer, button);
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return listener.keyDown(event, keycode) || super.keyDown(event, keycode);
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return listener.mouseMoved(event, x, y) || super.mouseMoved(event, x, y);
    }
}

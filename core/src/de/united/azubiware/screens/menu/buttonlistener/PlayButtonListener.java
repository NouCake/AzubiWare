package de.united.azubiware.screens.menu.buttonlistener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.united.azubiware.screens.menu.MenuButtonManager;


public class PlayButtonListener extends ClickListener {

    private MenuButtonManager menuButtonManager;
    private Button button;

    public PlayButtonListener(MenuButtonManager menuButtonManager, Button button){
        this.menuButtonManager = menuButtonManager;
        this.button = button;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(!this.button.isDisabled()) {
            menuButtonManager.playSound();
            if (this.button.isChecked() && menuButtonManager.isInQueue()) {
                menuButtonManager.quitQueue();
            } else {
                menuButtonManager.queueUp();
            }
        }
        return super.touchDown(event, x, y, pointer, button);
    }
}

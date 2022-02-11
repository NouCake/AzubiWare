package de.united.azubiware.screens.menu.buttonlistener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.united.azubiware.screens.menu.MenuButtonManager;


public class PlayButtonListener extends ClickListener {

    private MenuButtonManager menuButtonManager;
    private TextButton textButton;

    public PlayButtonListener(MenuButtonManager menuButtonManager, TextButton button){
        this.menuButtonManager = menuButtonManager;
        this.textButton = button;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(!this.textButton.isDisabled()) {
            menuButtonManager.playSound();
            if (this.textButton.isChecked() && menuButtonManager.isInQueue()) {
                menuButtonManager.quitQueue();
                textButton.setText("JOIN QUEUE");
            } else {
                menuButtonManager.queueUp();
                textButton.setText("LEAVE QUEUE");
            }
        }
        return super.touchDown(event, x, y, pointer, button);
    }
}

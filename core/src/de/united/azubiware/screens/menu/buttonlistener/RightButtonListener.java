package de.united.azubiware.screens.menu.buttonlistener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.united.azubiware.screens.menu.MenuButtonManager;

public class RightButtonListener extends ClickListener {

    private MenuButtonManager menuButtonManager;
    private Button button;

    public RightButtonListener(MenuButtonManager menuButtonManager, Button button){
        this.menuButtonManager = menuButtonManager;
        this.button = button;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(!this.button.isDisabled()){
            menuButtonManager.playSound();
            if(menuButtonManager.getPaginator().hasNext()){
                menuButtonManager.getPaginator().next();
            }else{
                menuButtonManager.getPaginator().prev();
            }
        }
        return super.touchDown(event, x, y, pointer, button);
    }
}

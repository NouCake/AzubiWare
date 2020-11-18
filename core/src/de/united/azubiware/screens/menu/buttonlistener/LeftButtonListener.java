package de.united.azubiware.screens.menu.buttonlistener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.united.azubiware.screens.menu.MenuButtonManager;

public class LeftButtonListener extends ClickListener {

    private MenuButtonManager menuButtonManager;
    private Button button;

    public LeftButtonListener(MenuButtonManager menuButtonManager, Button button){
        this.menuButtonManager = menuButtonManager;
        this.button = button;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(!this.button.isDisabled()){
            menuButtonManager.playSound();
            if(menuButtonManager.getPaginator().hasPrev()){
                menuButtonManager.getPaginator().prev();
            }else{
                menuButtonManager.getPaginator().next();
            }
        }
        return super.touchDown(event, x, y, pointer, button);
    }
}

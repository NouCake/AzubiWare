package de.united.azubiware.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ClosePopUp extends Group {

    private Image dark;
    private Image background;
    private Label label;

    private Button exit;
    private Button stay;

    private boolean hidden;

    public ClosePopUp(float stageWidth, float stageHeight){
        hidden = true;

        dark = new Image(new Texture(Gdx.files.internal("popup_dark.png")));
        dark.setWidth(stageWidth);
        dark.setHeight(stageHeight);
        dark.setPosition(stageWidth/2-dark.getWidth()/2, stageHeight/2-dark.getHeight()/2);
        dark.setVisible(false);
        addActor(dark);

        background = new Image(new Texture(Gdx.files.internal("popup_background.png")));
        background.setWidth(stageWidth/2);
        background.setHeight((stageWidth/2)*0.8f);
        background.setPosition(stageWidth/2-background.getWidth()/2, stageHeight/2-background.getHeight()/2);
        background.setVisible(false);
        addActor(background);

        exit = new Button(createButtonStyle("exit"));
        exit.setPosition(stageWidth/2-exit.getWidth()/2, stageHeight/2-exit.getHeight()*1.75f);
        exit.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!isHidden()){
                    Gdx.app.exit();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        exit.setVisible(false);
        addActor(exit);

        stay = new Button(createButtonStyle("stay"));
        stay.setPosition(stageWidth/2-stay.getWidth()/2, stageHeight/2 - stay.getHeight()/2);
        stay.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!isHidden()){
                    hide();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stay.setVisible(false);
        addActor(stay);
    }

    public Button.ButtonStyle createButtonStyle(String type){
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/" + type + "/button_" + type + "_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/" + type + "/button_" + type + "_down.png"))));

        buttonStyle.up = drawableUp;
        buttonStyle.down = drawableDown;
        buttonStyle.over = drawableDown;

        return buttonStyle;
    }

    public void show(){
        hidden = false;

        dark.setVisible(true);
        background.setVisible(true);

        exit.setVisible(true);
        stay.setVisible(true);
    }

    public void hide(){
        hidden = true;

        dark.setVisible(false);
        background.setVisible(false);

        exit.setVisible(false);
        stay.setVisible(false);
    }

    public boolean isHidden() {
        return hidden;
    }
}

package de.united.azubiware.utility;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.united.azubiware.AzubiWareGame;

public class ClosePopUp {

    private Image background;
    private Label label;

    private Button exit;
    private Button stay;

    private boolean hidden;

    public ClosePopUp(Stage stage, AzubiWareGame game){
        hidden = true;

        background = new Image(new Texture(Gdx.files.internal("popup_background.png")));
        background.setWidth(stage.getWidth());
        background.setHeight(stage.getWidth());
        background.setPosition(stage.getWidth()/2-background.getWidth()/2, stage.getHeight()/2-background.getWidth()/2);
        background.setVisible(false);
        stage.addActor(background);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getFont();
        labelStyle.fontColor = Color.DARK_GRAY;

        label = new Label("Are you sure", labelStyle);
        label.setPosition(stage.getWidth()/2-label.getWidth()/2, stage.getHeight()/1.5f+label.getHeight()*2);
        label.setVisible(false);
        stage.addActor(label);

        exit = new Button(creatButtonStyle("exit"));
        exit.setPosition(stage.getWidth()/2-exit.getWidth()/2, stage.getHeight()/2+exit.getHeight());
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
        stage.addActor(exit);

        stay = new Button(creatButtonStyle("stay"));
        stay.setPosition(stage.getWidth()/2-stay.getWidth()/2, stage.getHeight()/2-stay.getHeight()*0.25f);
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
        stage.addActor(stay);
    }

    public Button.ButtonStyle creatButtonStyle(String type){
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

        background.setVisible(true);
        label.setVisible(true);

        exit.setVisible(true);
        stay.setVisible(true);
    }

    public void hide(){
        hidden = true;

        background.setVisible(false);
        label.setVisible(false);

        exit.setVisible(false);
        stay.setVisible(false);
    }

    public boolean isHidden() {
        return hidden;
    }
}

package de.united.azubiware.utility.topper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class PlayerLabel extends Group {

    private final float topperScale = 1.25f;
    private final int padding = 10;

    private Image background;
    private Label label;

    private String playerName;

    public PlayerLabel(String playerName, String type, BitmapFont font){
        this.playerName = playerName;

        background = createBackground(type);
        setSize(background.getWidth() * topperScale, background.getHeight() * topperScale);
        label = createLabel(font);

        addActors();
    }

    private void addActors(){
        addActor(background);
        addActor(label);
    }

    private Image createBackground(String type){
        Image image = new Image(new Texture(Gdx.files.internal("games/topper_" + type + ".png")));
        image.setScale(topperScale);
        image.setPosition(0, 0);

        return image;
    }

    private Label.LabelStyle createLabelStyle(BitmapFont font){
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = font;
        labelStyle.fontColor =  Color.WHITE;

        return labelStyle;
    }

    private Label createLabel(BitmapFont font){
        Label label = new Label(playerName, createLabelStyle(font));

        label.setAlignment(Align.center);
        label.setWidth(getWidth() - 2 * padding);
        label.setFontScale(1.25f);
        label.setPosition(padding, background.getY() + getHeight()*0.5f - label.getHeight()*0.5f*topperScale);

        return label;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
        label.setText(playerName);
    }

}

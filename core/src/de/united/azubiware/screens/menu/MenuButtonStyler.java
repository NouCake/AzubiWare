package de.united.azubiware.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuButtonStyler {

    public Button.ButtonStyle createButtonStyle(String type){
        Texture playTextureUp = new Texture(Gdx.files.internal("buttons/" + type + "/button_" + type + ".png"));
        Drawable playDrawableUp = new TextureRegionDrawable(new TextureRegion(playTextureUp));

        Texture playTextureDown = new Texture(Gdx.files.internal("buttons/" + type + "/button_" + type + "_down.png"));
        Drawable playDrawableDown = new TextureRegionDrawable(new TextureRegion(playTextureDown));

        Button.ButtonStyle playStyle = new Button.ButtonStyle();

        playStyle.up = playDrawableUp;
        playStyle.down = playDrawableDown;
        playStyle.over = playDrawableDown;

        return playStyle;
    }

    public Button.ButtonStyle createArrowStyle(String direction){
        Texture blueTexture = new Texture(Gdx.files.internal("buttons/arrows/blue_slider" + direction + ".png"));
        Texture yellowTexture = new Texture(Gdx.files.internal("buttons/arrows/yellow_slider" + direction + ".png"));

        Drawable blueDrawable = new TextureRegionDrawable(new TextureRegion(blueTexture));
        Drawable yellowDrawable = new TextureRegionDrawable(new TextureRegion(yellowTexture));

        Button.ButtonStyle arrowStyle = new Button.ButtonStyle();

        arrowStyle.up = blueDrawable;
        arrowStyle.disabled = yellowDrawable;
        arrowStyle.over = yellowDrawable;
        arrowStyle.down = yellowDrawable;

        return arrowStyle;
    }

}

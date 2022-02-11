package de.united.azubiware.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.united.azubiware.AzubiWareGame;

public class MenuButtonStyler {

    public TextButton.TextButtonStyle createPlayButtonStyle(AzubiWareGame game){
        TextButton.TextButtonStyle playStyle = new TextButton.TextButtonStyle();

        Texture playTextureUp = new Texture(Gdx.files.internal("buttons/blue/blue_up.png"));
        playStyle.up = new TextureRegionDrawable(new TextureRegion(playTextureUp));

        Texture playTextureDown = new Texture(Gdx.files.internal("buttons/blue/blue_up.png"));
        Drawable playDrawableDown = new TextureRegionDrawable(new TextureRegion(playTextureDown));

        playStyle.down = playDrawableDown;
        playStyle.over = playDrawableDown;

        Texture quitTextureUp = new Texture(Gdx.files.internal("buttons/yellow/yellow_up.png"));
        playStyle.checked = new TextureRegionDrawable(new TextureRegion(quitTextureUp));

        Texture quitTextureDown = new Texture(Gdx.files.internal("buttons/yellow/yellow_down.png"));
        Drawable quitDrawableDown = new TextureRegionDrawable(new TextureRegion(quitTextureDown));

        playStyle.checkedDown = quitDrawableDown;
        playStyle.checkedOver = quitDrawableDown;

        playStyle.font = game.getFont();
        playStyle.fontColor = Color.WHITE;

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

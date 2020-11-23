package de.united.azubiware.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuButtonStyler {

    public Button.ButtonStyle createPlayButtonStyle(){
        Button.ButtonStyle playStyle = new Button.ButtonStyle();

        Texture playTextureUp = new Texture(Gdx.files.internal("buttons/play/button_play.png"));
        playStyle.up = new TextureRegionDrawable(new TextureRegion(playTextureUp));

        Texture playTextureDown = new Texture(Gdx.files.internal("buttons/play/button_play_down.png"));
        Drawable playDrawableDown = new TextureRegionDrawable(new TextureRegion(playTextureDown));

        playStyle.down = playDrawableDown;
        playStyle.over = playDrawableDown;

        Texture quitTextureUp = new Texture(Gdx.files.internal("buttons/quit/button_quit.png"));
        playStyle.checked = new TextureRegionDrawable(new TextureRegion(quitTextureUp));

        Texture quitTextureDown = new Texture(Gdx.files.internal("buttons/quit/button_quit_down.png"));
        Drawable quitDrawableDown = new TextureRegionDrawable(new TextureRegion(quitTextureDown));

        playStyle.checkedDown = quitDrawableDown;
        playStyle.checkedOver = quitDrawableDown;


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

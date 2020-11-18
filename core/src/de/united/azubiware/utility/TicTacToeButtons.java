package de.united.azubiware.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TicTacToeButtons {

    public static Button.ButtonStyle generateButtonStyle(){
        Texture textureUp = new Texture(Gdx.files.internal("buttons/ttt/grey_button10.png"));
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(textureUp));

        Texture textureDown = new Texture(Gdx.files.internal("buttons/ttt/green_button08.png"));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(textureDown));

        Texture textureDisabled = new Texture(Gdx.files.internal("buttons/ttt/red_button05.png"));
        Drawable drawableDisabled = new TextureRegionDrawable(new TextureRegion(textureDisabled));

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        buttonStyle.up = drawableUp;
        buttonStyle.down = drawableDown;
        buttonStyle.over = drawableDown;
        buttonStyle.checked = drawableDown;
        buttonStyle.disabled = drawableDisabled;

        return buttonStyle;
    }

}

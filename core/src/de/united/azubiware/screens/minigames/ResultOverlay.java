package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ResultOverlay {

    private Stage stage;

    private Image winImage;
    private Image loseImage;

    private Image dark;

    private int result = 0;
    private boolean showResult = false;

    public ResultOverlay(Stage stage){
        this.stage = stage;

        dark = new Image(new Texture(Gdx.files.internal("popup_dark.png")));
        dark.setHeight(stage.getHeight());
        dark.setWidth(stage.getWidth());
        dark.setPosition(stage.getWidth()/2-dark.getWidth()/2, stage.getHeight()/2-dark.getHeight()/2);
        dark.setVisible(false);

        winImage = new Image(new Texture(Gdx.files.internal("games/win_overlay.png")));
        winImage.setPosition(stage.getWidth()/2-winImage.getWidth()/2, stage.getHeight()/2-winImage.getWidth()/2);
        winImage.setVisible(false);

        loseImage = new Image(new Texture(Gdx.files.internal("games/lose_overlay.png")));
        loseImage.setPosition(stage.getWidth()/2-loseImage.getWidth()/2, stage.getHeight()/2-loseImage.getHeight()/2);
        loseImage.setVisible(false);

        stage.addActor(dark);
        stage.addActor(loseImage);
        stage.addActor(winImage);
    }


    public void setResult(int result){
        dark.setVisible(true);
        showResult = true;
        if(result == 1){
            winImage.setVisible(true);
        }else{
            loseImage.setVisible(true);
        }
    }

    public boolean isShowResult() {
        return showResult;
    }
}

package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

public class ResultOverlay {

    private Stage stage;

    private Image winImage;
    private Image loseImage;
    private Image drawImage;

    private Button leave;

    private Image dark;

    private int result = 0;
    private boolean showResult = false;
    private boolean switchToMenu = false;

    private long finished;

    public ResultOverlay(Stage stage){
        this.stage = stage;

        dark = new Image(new Texture(Gdx.files.internal("popup_dark.png")));
        dark.setHeight(stage.getHeight());
        dark.setWidth(stage.getWidth());
        dark.setPosition(stage.getWidth()/2-dark.getWidth()/2, stage.getHeight()/2-dark.getHeight()/2);
        dark.setVisible(false);

        winImage = new Image(new Texture(Gdx.files.internal("games/win_overlay.png")));
        winImage.setPosition(stage.getWidth()/2-winImage.getWidth()/2, stage.getHeight()/2+winImage.getWidth()/2.5f);
        winImage.setVisible(false);

        loseImage = new Image(new Texture(Gdx.files.internal("games/lose_overlay.png")));
        loseImage.setPosition(stage.getWidth()/2-loseImage.getWidth()/2, stage.getHeight()/2+loseImage.getHeight()/2.5f);
        loseImage.setVisible(false);

        drawImage = new Image(new Texture(Gdx.files.internal("games/draw_overlay.png")));
        drawImage.setPosition(stage.getWidth()/2-drawImage.getWidth()/2, stage.getHeight()/2+drawImage.getHeight()/2.5f);
        drawImage.setVisible(false);

        leave = new Button(createButtonStyle());
        leave.setPosition(stage.getWidth()/2f - leave.getWidth(),stage.getHeight()/2f-leave.getHeight());
        leave.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!leave.isDisabled() && showResult){
                    switchToMenu = true;
                }
                return true;
            }
        });
        leave.setVisible(false);

        stage.addActor(dark);
        stage.addActor(loseImage);
        stage.addActor(winImage);
    }


    public void setResult(int result){
        if(!showResult) {
            finished = TimeUtils.millis();

            dark.setVisible(true);
            showResult = true;
            if (result == 0) {
                drawImage.setVisible(true);
            } else if (result == 1) {
                winImage.setVisible(true);
            } else {
                loseImage.setVisible(true);
            }
            leave.setVisible(true);
        }
    }

    public boolean isShowResult() {
        return showResult;
    }

    private Button.ButtonStyle createButtonStyle(){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.over = drawableDown;

        return style;
    }

    public long getFinished() {
        return finished;
    }

    public boolean isSwitchToMenu() {
        return switchToMenu;
    }
}

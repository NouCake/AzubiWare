package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class ResultOverlay extends Group {

    private final Image winImage;
    private final Image loseImage;
    private final Image drawImage;

    private final Button leave;

    private final Image dark;

    private final int result = 0;
    private boolean showResult = false;
    private boolean switchToMenu = false;

    private long finished;

    public ResultOverlay(float stageWidth, float stageHeight){

        dark = new Image(new Texture(Gdx.files.internal("popup_dark.png")));
        dark.setHeight(stageHeight);
        dark.setWidth(stageWidth);
        dark.setPosition(stageWidth/2-dark.getWidth()/2, stageHeight/2-dark.getHeight()/2);
        dark.setVisible(false);

        winImage = new Image(new Texture(Gdx.files.internal("games/overlays/win_overlay.png")));
        winImage.setPosition(stageWidth/2, stageHeight/2, Align.center | Align.bottom);
        winImage.setVisible(false);

        loseImage = new Image(new Texture(Gdx.files.internal("games/overlays/lose_overlay.png")));
        loseImage.setPosition(stageWidth/2, stageHeight/2, Align.center | Align.bottom);
        loseImage.setVisible(false);

        drawImage = new Image(new Texture(Gdx.files.internal("games/overlays/draw_overlay.png")));
        drawImage.setPosition(stageWidth/2, stageHeight/2, Align.center | Align.bottom);
        drawImage.setVisible(false);

        leave = new Button(createButtonStyle());
        leave.setPosition(stageWidth/2f ,stageHeight/2f, Align.center | Align.top);
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

        addActor(dark);
        addActor(loseImage);
        addActor(winImage);
        addActor(drawImage);
        addActor(leave);
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
        return ((showResult && (TimeUtils.millis() - finished) >= 5000)) || switchToMenu;
    }
}

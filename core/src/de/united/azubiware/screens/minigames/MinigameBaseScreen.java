package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.menu.MainMenuScreen;
import de.united.azubiware.utility.ClosePopUp;

public class MinigameBaseScreen extends ScreenAdapter {

    private final AzubiWareGame game;
    private final Stage stage;
    private final ResultOverlay resultOverlay;
    private final ClosePopUp closePopup;
    private final Button btnLeave;

    public MinigameBaseScreen(AzubiWareGame game, IUser ...oponents) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        resultOverlay = new ResultOverlay(stage);
        closePopup = new ClosePopUp(stage, game);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);


        addBackground();
        addCloseListener();
        btnLeave = createLeaveButton();
    }

    private void addBackground(){
        float stageRatio = stage.getWidth() / stage.getHeight();

        Image bg = new Image(new Texture(Gdx.files.internal("backgrounds/backgroundCastles.png")));
        float bgRatio = bg.getWidth() / bg.getHeight();

        System.out.println(stage.getWidth() + " | " + stage.getHeight());
        if(bgRatio > stageRatio){
            float scaling = stage.getHeight() / bg.getHeight();
            bg.setScale(scaling);
            bg.setPosition(stage.getWidth()*0.5f - bg.getWidth()*0.5f*scaling, stage.getHeight()*0.5f - bg.getHeight()*0.5f*scaling);
        } else {
            float scaling = stage.getWidth() / bg.getWidth();
            bg.setScale(scaling);
            bg.setPosition(stage.getWidth()*0.5f - bg.getWidth()*0.5f*scaling, stage.getHeight()*0.5f - bg.getHeight()*0.5f*scaling);
        }

        Image footer = new Image(new Texture(Gdx.files.internal("games/ttt_bottom.png")));
        footer.setScale(stage.getWidth() / footer.getWidth());
        footer.setPosition(0, 0);


        stage.addActor(bg);
        stage.addActor(footer);
    }
    private Button createLeaveButton(){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.over = drawableDown;

        Button btn =  new Button(style);
        btn.setPosition(stage.getWidth()/2f - btn.getWidth()/2f, 10);
        stage.addActor(btn);

        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!btn.isDisabled()){
                    game.getClient().sendMatchLeave();
                    dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        return btn;
    }
    private void addCloseListener(){
        stage.addListener(new ClickListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
                    if(!resultOverlay.isShowResult()) {
                        if (closePopup.isHidden()) {
                            closePopup.show();
                        } else {
                            closePopup.hide();
                        }
                    }
                }
                return super.keyDown(event, keycode);
            }
        });
    }
    @Override public void render(float delta) {
        stage.act();
        stage.draw();

        if(resultOverlay.isSwitchToMenu()){
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }

        super.render(delta);
    }
    @Override public void dispose() {
        super.dispose();
        stage.dispose();
    }

    protected Stage getStage(){
        return stage;
    }
    protected AzubiWareGame getGame(){
        return game;
    }

    protected boolean isInputBlocked(){
        return !closePopup.isHidden();
    }

    public ResultOverlay getResultOverlay() {
        return resultOverlay;
    }
}

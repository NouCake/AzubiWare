package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.minigames.IGame;
import de.united.azubiware.utility.ClosePopUp;

public class WaitingScreen extends ScreenAdapter {

    private IGame miniGame;
    private AzubiWareGame game;
    private Stage stage;

    private ClosePopUp popUp;
    private Sprite backgroundSprite;

    public WaitingScreen(AzubiWareGame game, IGame iGame){
        this.game = game;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        backgroundSprite = new Sprite(iGame.getBackground());

        Image image = new Image(iGame.getSplash());
        image.setPosition(stage.getWidth()/2-image.getWidth()/2, stage.getHeight()/2);
        stage.addActor(image);

        popUp = new ClosePopUp(stage, game);
        stage.addListener(new ClickListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
                    if(popUp.isHidden()){
                        popUp.show();
                    }else{
                        popUp.hide();
                    }
                }
                return super.keyDown(event, keycode);
            }
        });
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
    }

    private boolean switchToMatch = false;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        drawBackground();
        stage.draw();

        if(switchToMatch){
            dispose();
            game.setScreen(miniGame.createStage(game));
        }
    }

    public void setSwitchToMatch(boolean switchToMatch) {
        this.switchToMatch = switchToMatch;
    }
}

package de.united.azubiware.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.screens.login.LoginScreen;

public class SplashScreen implements Screen {

    final AzubiWareGame game;
    Stage stage;
    long startTime;

    Texture backgroundTexture;
    Sprite backgroundSprite;

    public SplashScreen(AzubiWareGame game){
        this.game = game;
        startTime = TimeUtils.millis();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/backgroundForest.png"));
        backgroundSprite = new Sprite(backgroundTexture);

        Texture texture = new Texture("splash.png");
        Image image = new Image(texture);
        image.setPosition(stage.getWidth()/2f-image.getWidth()/2,stage.getHeight()/2f-image.getHeight()/2);

        stage.addActor(image);
    }

    @Override
    public void show() {

    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
    }

    @Override
    public void render(float delta) {
        if(TimeUtils.millis() - startTime <= 1000) {
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act();
            drawBackground();
            stage.draw();
        }else{
            try {
                if(game.getClient().isConnected()) {
                    dispose();
                    game.setScreen(new LoginScreen(game));
                }else{
                    startTime = TimeUtils.millis();
                }
            }catch (NullPointerException exception){
                startTime = TimeUtils.millis();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

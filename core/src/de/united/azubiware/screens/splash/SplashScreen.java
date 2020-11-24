package de.united.azubiware.screens.splash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.screens.login.LoginScreen;

public class SplashScreen extends ScreenAdapter {

    final AzubiWareGame game;
    Stage stage;
    long startTime;

    Sprite backgroundSprite;
    Image image;
    Label label;

    public SplashScreen(AzubiWareGame game){
        this.game = game;
        startTime = TimeUtils.millis();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("backgrounds/backgroundForest.png")));

        image = new Image(new Texture("splash.png"));
        image.setWidth(stage.getWidth()*0.75f);
        image.setHeight(image.getWidth());
        image.setPosition(stage.getWidth()/2f-image.getWidth()/2,stage.getHeight()/2f-image.getHeight()/2);

        createStateLabel();
        stage.addActor(image);

        game.getClient().setClientLister(new SplashScreenPacketListener(this));
    }

    public void createStateLabel(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getFont();
        labelStyle.fontColor = Color.DARK_GRAY;

        label = new Label("Connecting to server", labelStyle);
        label.setAlignment(Align.center);
        label.setWidth(300);
        label.setHeight(40);
        label.setPosition(stage.getWidth()/2f-label.getWidth()/2, image.getY() + label.getHeight()*1.5f);

        stage.addActor(label);
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
    }

    @Override
    public void render(float delta) {
        if(TimeUtils.millis() - startTime <= 2000) {
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act();
            drawBackground();
            stage.draw();
        }else{
            if(game.getClient().isConnected()) {
                dispose();
                game.setScreen(new LoginScreen(game));
            }else{
                startTime = TimeUtils.millis();
            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setState(String state){
        label.setText(state);
    }
}

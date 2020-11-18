package de.united.azubiware.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.screens.minigames.TicTacToeScreen;
import de.united.azubiware.utility.Clouds;
import de.united.azubiware.utility.MiniGamePaginator;

import java.util.Random;

public class MainMenuScreen extends ScreenAdapter {

    final AzubiWareGame game;

    private Clouds clouds;
    private MenuButtonManager buttonManager;

    Button leftButton;
    Button rightButton;

    Label label;

    Texture backgroundTexture;
    Sprite backgroundSprite;

    MiniGamePaginator paginator;

    Stage stage;

    public MainMenuScreen(AzubiWareGame game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/backgroundForest.png"));
        backgroundSprite = new Sprite(backgroundTexture);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getFont();
        labelStyle.fontColor = Color.DARK_GRAY;

        label = new Label(new Random().nextInt(10)+1 + " In queue", labelStyle);
        label.setAlignment(Align.center);
        label.setWidth(300);
        label.setHeight(40);
        label.setPosition(stage.getWidth()/2f-150, stage.getHeight()/4.5f-55);
        label.setVisible(false);

        stage.addActor(label);
        paginator = new MiniGamePaginator(stage);
        buttonManager = new MenuButtonManager(stage, paginator);
        clouds = new Clouds(stage);
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(buttonManager.isInQueue()) {
            if(!label.isVisible())
                label.setVisible(true);
            updateWaiting();
            paginator.waiting();
        }else if(label.isVisible()){
            label.setVisible(false);
        }

        stage.act();
        drawBackground();
        stage.draw();
        clouds.moveClouds();

        paginator.paginate();
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    long lastUpdated = TimeUtils.millis();
    int waiting = 6;

    public void updateWaiting(){
        if(TimeUtils.millis()-lastUpdated >= 1000){
            if(new Random().nextBoolean())
                waiting = new Random().nextInt(10)+1;
            label.setText(waiting + " in queue");
            lastUpdated = TimeUtils.millis();
            /*
            if(new Random().nextBoolean() && new Random().nextBoolean()){
                if(paginator.getCurrent() == 0) {
                    dispose();
                    game.setScreen(new TicTacToeScreen(game));
                }
            }
             */
        }
    }

}

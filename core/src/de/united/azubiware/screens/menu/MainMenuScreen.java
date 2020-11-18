package de.united.azubiware.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class MainMenuScreen implements Screen {

    final AzubiWareGame game;
    private Clouds clouds;

    Button playButton;
    Button quitButton;

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

        MenuButtonStyler buttonStyler = new MenuButtonStyler();

        this.playButton = new Button(buttonStyler.createButtonStyle("play"));
        this.quitButton = new Button(buttonStyler.createButtonStyle("quit"));

        playButton.setPosition((stage.getWidth()/2f)-95, stage.getHeight()/4.5f);

        quitButton.setPosition((stage.getWidth()/2f)-95, stage.getHeight()/4.5f);
        quitButton.setVisible(false);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getFont();
        labelStyle.fontColor = Color.DARK_GRAY;

        label = new Label(new Random().nextInt(10)+1 + " In queue", labelStyle);
        label.setAlignment(Align.center);
        label.setWidth(300);
        label.setHeight(40);
        label.setPosition(stage.getWidth()/2f-150, playButton.getY() - playButton.getHeight()/2 - 30);
        label.setVisible(false);

        leftButton = new Button(buttonStyler.createArrowStyle("Left"));
        leftButton.setPosition(playButton.getX()-(playButton.getWidth()/2), stage.getHeight()/4.5f);
        leftButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!leftButton.isDisabled()){
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                    sound.play();
                    if(paginator.hasPrev()) {
                        paginator.prev();
                    }else{
                        paginator.next();
                    }
                }
                return true;
            }
        });

        rightButton = new Button(buttonStyler.createArrowStyle("Right"));
        rightButton.setPosition(playButton.getX()+(playButton.getWidth()/2)+rightButton.getWidth()*2, stage.getHeight()/4.5f);
        rightButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!rightButton.isDisabled()){
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                    sound.play();
                    if(paginator.hasNext()) {
                        paginator.next();
                    }else{
                        paginator.prev();
                    }
                }
                return true;
            }
        });

        playButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(playButton.isVisible() && !playButton.isDisabled()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                    sound.play();
                    playButton.setVisible(false);
                    quitButton.setVisible(true);

                    leftButton.setDisabled(true);
                    rightButton.setDisabled(true);

                    label.setVisible(true);
                    /*
                    JOIN QUEUE
                     */
                    return true;
                }
                return false;
            }
        });

        quitButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(quitButton.isVisible() && !quitButton.isDisabled()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                    sound.play();
                    quitButton.setVisible(false);
                    playButton.setVisible(true);

                    leftButton.setDisabled(false);
                    rightButton.setDisabled(false);

                    label.setVisible(false);
                    paginator.reset();
                    /*
                    QUIT QUEUE
                     */
                    return true;
                }
                return false;
            }
        });

        stage.addActor(label);
        stage.addActor(playButton);
        stage.addActor(quitButton);

        stage.addActor(leftButton);
        stage.addActor(rightButton);

        paginator = new MiniGamePaginator(stage);
        clouds = new Clouds(stage);
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(quitButton.isVisible()) {
            updateWaiting();
            paginator.waiting();
        }

        stage.act();
        drawBackground();
        stage.draw();
        clouds.moveClouds();

        paginator.paginate();
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

    long lastUpdated = TimeUtils.millis();
    int waiting = 6;

    public void updateWaiting(){
        if(TimeUtils.millis()-lastUpdated >= 1000){
            if(new Random().nextBoolean())
                waiting = new Random().nextInt(10)+1;
            label.setText(waiting + " in queue");
            lastUpdated = TimeUtils.millis();
            if(new Random().nextBoolean() && new Random().nextBoolean()){
                if(paginator.getCurrent() == 0) {
                    dispose();
                    game.setScreen(new TicTacToeScreen(game));
                }
            }
        }
    }

}

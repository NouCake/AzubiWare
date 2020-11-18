package de.united.azubiware.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.screens.minigames.TicTacToeScreen;
import de.united.azubiware.utility.MiniGamePaginator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainMenuScreen implements Screen {

    final AzubiWareGame game;

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

        this.playButton = new Button(createPlayButtonStyle());
        this.quitButton = new Button(createQuitButtonStyle());

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

        leftButton = new Button(createArrowStyle("Left"));
        leftButton.setPosition(playButton.getX()-(playButton.getWidth()/2), stage.getHeight()/4.5f);
        leftButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                sound.play();
                if(!leftButton.isDisabled()){
                    if(paginator.hasPrev()) {
                        paginator.prev();
                    }else{
                        paginator.next();
                    }
                }
                return true;
            }
        });

        rightButton = new Button(createArrowStyle("Right"));
        rightButton.setPosition(playButton.getX()+(playButton.getWidth()/2)+rightButton.getWidth()*2, stage.getHeight()/4.5f);
        rightButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                sound.play();
                if(!rightButton.isDisabled()){
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

        createClouds();
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
        moveClouds();

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

    public Button.ButtonStyle createPlayButtonStyle(){
        Texture playTextureUp = new Texture(Gdx.files.internal("buttons/play/button_play.png"));
        Drawable playDrawableUp = new TextureRegionDrawable(new TextureRegion(playTextureUp));

        Texture playTextureDown = new Texture(Gdx.files.internal("buttons/play/button_play_down.png"));
        Drawable playDrawableDown = new TextureRegionDrawable(new TextureRegion(playTextureDown));

        Button.ButtonStyle playStyle = new Button.ButtonStyle();

        playStyle.up = playDrawableUp;
        playStyle.down = playDrawableDown;
        playStyle.over = playDrawableDown;

        return playStyle;
    }

    public Button.ButtonStyle createQuitButtonStyle(){
        Texture quitTextureUp = new Texture(Gdx.files.internal("buttons/quit/button_quit.png"));
        Drawable quitDrawableUp = new TextureRegionDrawable(new TextureRegion(quitTextureUp));

        Texture quitTextureDown = new Texture(Gdx.files.internal("buttons/quit/button_quit_down.png"));
        Drawable quitDrawableDown = new TextureRegionDrawable(new TextureRegion(quitTextureDown));

        Button.ButtonStyle quitStyle = new Button.ButtonStyle();

        quitStyle.up = quitDrawableUp;
        quitStyle.down = quitDrawableDown;
        quitStyle.over = quitDrawableDown;

        return quitStyle;
    }

    public Button.ButtonStyle createArrowStyle(String direction){
        Texture blueTexture = new Texture(Gdx.files.internal("buttons/arrows/blue_slider" + direction + ".png"));
        Texture yellowTexture = new Texture(Gdx.files.internal("buttons/arrows/yellow_slider" + direction + ".png"));

        Drawable blueDrawable = new TextureRegionDrawable(new TextureRegion(blueTexture));
        Drawable yellowDrawable = new TextureRegionDrawable(new TextureRegion(yellowTexture));

        Button.ButtonStyle arrowStyle = new Button.ButtonStyle();

        arrowStyle.up = blueDrawable;
        arrowStyle.disabled = yellowDrawable;
        arrowStyle.over = yellowDrawable;
        arrowStyle.down = yellowDrawable;

        return arrowStyle;
    }

    List<Image> clouds;

    public void createClouds(){
        clouds = new ArrayList<>();
        int cloudAmount = 4+new Random().nextInt(4);
        for(int i = 0; i < cloudAmount; i++){
            Texture texture = new Texture(Gdx.files.internal("backgrounds/clouds/cloud" + (new Random().nextInt(8)+1) + ".png"));
            Image image = new Image(texture);
            image.setSize(90f, 90f);
            image.setPosition(stage.getWidth()/2f, stage.getHeight()/2f);
            if(new Random().nextInt(30) >= 14){
                image.setPosition(stage.getWidth()/2f-(new Random().nextInt((int) (stage.getWidth()/2))+2), stage.getHeight()-(100+(new Random().nextInt(10))));
            }else{
                image.setPosition(stage.getWidth()/2f+(new Random().nextInt((int) (stage.getWidth()/2))+2), stage.getHeight()-(100+(new Random().nextInt(10))));
            }
            clouds.add(image);
            stage.addActor(image);
        }
    }

    public void moveClouds(){
        for(Image cloud : clouds){
            if(cloud.getX() < stage.getWidth()+95) {
                cloud.setX(cloud.getX() + 0.15f);
            }else{
                cloud.setX(-100);
            }
        }
    }

}

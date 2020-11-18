package de.united.azubiware.screens.login;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.screens.menu.MainMenuScreen;

public class LoginScreen implements Screen {

    final AzubiWareGame game;

    private int minUsernameLength = 5;
    private int maxUsernameLength = 15;

    TextField usernameField;
    Button playButton;

    Texture backgroundTexture;
    Sprite backgroundSprite;

    Stage stage;

    public LoginScreen(final AzubiWareGame game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/backgroundForest.png"));
        backgroundSprite = new Sprite(backgroundTexture);

        TextField.TextFieldStyle textStyle = new TextField.TextFieldStyle();
        textStyle.fontColor = Color.DARK_GRAY;
        textStyle.font = game.getFont();
        textStyle.messageFont = game.getFont();
        textStyle.messageFontColor = Color.DARK_GRAY;
        textStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textfield.png"))));

        this.usernameField = new TextField("", textStyle);
        usernameField.setMessageText("username");
        usernameField.setMaxLength(maxUsernameLength);
        usernameField.setAlignment(Align.center);
        usernameField.setHeight(50);
        usernameField.setWidth(250);

        Texture playTexture = new Texture(Gdx.files.internal("buttons/login/button_login.png"));
        Texture playTextureDown = new Texture(Gdx.files.internal("buttons/login/button_login_down.png"));
        Drawable playDrawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        Drawable playDrawableDown = new TextureRegionDrawable(new TextureRegion(playTextureDown));

        Button.ButtonStyle testStyle = new Button.ButtonStyle();
        testStyle.up = playDrawable;
        testStyle.down = playDrawableDown;
        testStyle.over = playDrawableDown;

        this.playButton = new Button(testStyle);

        usernameField.setPosition(stage.getWidth()/2f-125, stage.getHeight()/2f+15);
        playButton.setPosition(stage.getWidth()/2f-95, stage.getHeight()/2.5f);

        playButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(playButton.isVisible() && !playButton.isDisabled()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                    sound.play();
                    if(usernameField.getText().length() >= minUsernameLength && usernameField.getText().length() < usernameField.getMaxLength()){
                        //game.getClient().sendLogin(usernameField.getText());
                        dispose();
                        game.setScreen(new MainMenuScreen(game));
                    }
                    return true;
                }
                return false;
            }

        });

        stage.addActor(usernameField);
        stage.addActor(playButton);
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        drawBackground();
        stage.draw();
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

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    Label label;

    Sprite backgroundSprite;

    Stage stage;

    public LoginScreen(final AzubiWareGame game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("backgrounds/backgroundForest.png")));

        createUsernameField();
        createLoginButton();
        createStateLabel();

        playButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(playButton.isVisible() && !playButton.isDisabled()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                    sound.play();
                    if(usernameField.getText().length() >= minUsernameLength && usernameField.getText().length() < usernameField.getMaxLength()){
                        game.getClient().sendLogin(usernameField.getText());
                        dispose();
                        game.setScreen(new MainMenuScreen(game));
                    }
                    return true;
                }
                return false;
            }

        });
    }

    public void createUsernameField(){
        TextField.TextFieldStyle textStyle = new TextField.TextFieldStyle();
        textStyle.fontColor = Color.DARK_GRAY;
        textStyle.font = game.getFont();
        textStyle.messageFont = game.getFont();
        textStyle.messageFontColor = Color.DARK_GRAY;
        textStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textfield.png"))));

        usernameField = new TextField("", textStyle);
        usernameField.setMessageText("username");
        usernameField.setMaxLength(maxUsernameLength);
        usernameField.setAlignment(Align.center);
        usernameField.setWidth(stage.getWidth()*0.5f);

        usernameField.setPosition(stage.getWidth()/2f-usernameField.getWidth()/2, stage.getHeight()/2f+usernameField.getHeight()/2);

        stage.addActor(usernameField);
    }

    public void createLoginButton(){
        Texture playTexture = new Texture(Gdx.files.internal("buttons/login/button_login.png"));
        Texture playTextureDown = new Texture(Gdx.files.internal("buttons/login/button_login_down.png"));
        Drawable playDrawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        Drawable playDrawableDown = new TextureRegionDrawable(new TextureRegion(playTextureDown));

        Button.ButtonStyle testStyle = new Button.ButtonStyle();
        testStyle.up = playDrawable;
        testStyle.down = playDrawableDown;
        testStyle.over = playDrawableDown;

        playButton = new Button(testStyle);

        playButton.setPosition(stage.getWidth()/2f-playButton.getWidth()/2, usernameField.getY()-usernameField.getHeight()/1.5f-playButton.getHeight()/2);

        stage.addActor(playButton);
    }

    public void createStateLabel(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getFont();
        labelStyle.fontColor = Color.DARK_GRAY;

        label = new Label("", labelStyle);
        label.setAlignment(Align.center);
        label.setWidth(300);
        label.setHeight(40);
        label.setPosition(stage.getWidth()/2f-label.getWidth()/2, playButton.getY() + label.getHeight()*1.5f);

        stage.addActor(label);
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

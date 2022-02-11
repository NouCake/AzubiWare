package de.united.azubiware.screens.login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.screens.menu.MainMenuScreen;

public class LoginScreen extends ScreenAdapter {

    final AzubiWareGame game;

    private final int minUsernameLength = 3;
    private final int maxUsernameLength = 15;

    TextField usernameField;
    TextButton loginButton;
    Label label;

    Sprite backgroundSprite;

    Stage stage;

    private long loginTime;

    private boolean triedLogin = false;
    private boolean loginSuccess = false;

    public LoginScreen(final AzubiWareGame game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        game.getClient().setClientLister(new LoginScreenPacketListener(this));

        Texture texture = new Texture(Gdx.files.internal("backgrounds/backgroundForest.png"));
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        backgroundSprite = new Sprite(texture);

        createUsernameField();
        createLoginButton();
        createStateLabel();

        loginButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(loginButton.isVisible() && !loginButton.isDisabled()) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click2.ogg"));
                    sound.play();
                    if(usernameField.getText().length() >= minUsernameLength && usernameField.getText().length() < usernameField.getMaxLength()){
                        game.getClient().sendLogin(usernameField.getText());
                        usernameField.setDisabled(true);
                        loginButton.setDisabled(true);

                        loginTime = TimeUtils.millis();
                        triedLogin = true;
                    }
                    return true;
                }
                return false;
            }

        });

        stage.addActor(loginButton);
        stage.addActor(usernameField);
        stage.addActor(label);
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
        usernameField.setDisabled(false);

        usernameField.setPosition(stage.getWidth()/2f-usernameField.getWidth()/2, stage.getHeight()/2f+usernameField.getHeight()/2);
    }

    public void createLoginButton(){
        Texture playTexture = new Texture(Gdx.files.internal("buttons/orange/orange_up.png"));
        Texture playTextureDown = new Texture(Gdx.files.internal("buttons/orange/orange_down.png"));
        Drawable playDrawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        Drawable playDrawableDown = new TextureRegionDrawable(new TextureRegion(playTextureDown));

        TextButton.TextButtonStyle testStyle = new TextButton.TextButtonStyle();
        testStyle.up = playDrawable;
        testStyle.down = playDrawableDown;
        testStyle.over = playDrawableDown;

        testStyle.fontColor = Color.WHITE;
        testStyle.font = game.getFont();

        loginButton = new TextButton("LOGIN", testStyle);

        loginButton.align(Align.center);
        loginButton.setPosition(stage.getWidth()/2f-loginButton.getWidth()/2, usernameField.getY()-usernameField.getHeight()/1.5f-loginButton.getHeight()/2);
    }

    public void createStateLabel(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getFont();
        labelStyle.fontColor = Color.DARK_GRAY;

        label = new Label("", labelStyle);
        label.setAlignment(Align.center);
        label.setWidth(300);
        label.setHeight(40);
        label.setPosition(stage.getWidth()/2f-label.getWidth()/2, loginButton.getY() - label.getHeight()*1.5f);
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        drawBackground();
        stage.draw();

        if(triedLogin){
            if(TimeUtils.millis() - loginTime >= 750) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }else if(!loginSuccess) {
                loginTime = TimeUtils.millis();;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().update();
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setState(String state){
        label.setText(state);
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }
}

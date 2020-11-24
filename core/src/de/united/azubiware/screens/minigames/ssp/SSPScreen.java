package de.united.azubiware.screens.minigames.ssp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;

public class SSPScreen extends ScreenAdapter {

    private AzubiWareGame game;
    private IUser opponent;

    private Stage stage;

    private Label roundLabel;
    private int round = 1;

    private final Sprite backgroundSprite;

    public SSPScreen(AzubiWareGame game, IUser opponent){
        this.game = game;
        this.opponent = opponent;

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("backgrounds/backgroundDesert.png")));
    }

    public void createRoundLabel(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = game.getFont();
        labelStyle.fontColor = Color.WHITE;
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
    }

    @Override
    public void render(float delta) {
        stage.act();
        drawBackground();
        stage.draw();
    }
}

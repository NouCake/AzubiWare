package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;
import de.united.azubiware.minigames.IGame;
import de.united.azubiware.utility.ClosePopUp;

public class WaitingScreen extends ScreenAdapter {

    IGame miniGame;
    AzubiWareGame game;
    private Stage stage;

    private ClosePopUp popUp;
    private Sprite backgroundSprite;

    private IUser[] opponents;

    private Screen nextScreen;

    public WaitingScreen(AzubiWareGame game, IGame iGame, IUser[] opponents){
        this.game = game;
        this.miniGame = iGame;
        this.stage = new Stage(new ScreenViewport());

        this.opponents = opponents;

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        backgroundSprite = new Sprite(iGame.getWaitingScreenBackground());

        Image image = new Image(iGame.getSplash());
        image.setSize(stage.getWidth()*0.5f, (stage.getWidth()*0.5f));
        image.setPosition(stage.getWidth()/2-image.getWidth()/2, stage.getHeight()/2);
        stage.addActor(image);

        if(opponents.length > 0) {
            Label.LabelStyle labelStyle = new Label.LabelStyle();

            labelStyle.font = game.getFont();
            labelStyle.fontColor = Color.DARK_GRAY;

            Label opponent = new Label("Opponents", labelStyle);
            opponent.setPosition(stage.getWidth()/2-opponent.getWidth()/2, image.getY()-opponent.getHeight());
            stage.addActor(opponent);
            int amount = 1;
            for (IUser user : opponents) {
                Label userLabel = new Label(user.getName(), labelStyle);
                userLabel.setPosition(stage.getWidth()/2-userLabel.getWidth()/2, opponent.getY()-opponent.getHeight()/2-(amount*userLabel.getHeight()/2));
                stage.addActor(userLabel);
                amount++;
            }
        }

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        drawBackground();
        stage.draw();

        if(nextScreen != null){
            dispose();
            game.setScreen(nextScreen);
        }
    }

    public void setSwitchToMatch(Screen screen) {
        this.nextScreen = screen;
    }
}

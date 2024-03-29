package de.united.azubiware.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;
import de.united.azubiware.minigames.interfaces.IGame;
import de.united.azubiware.screens.minigames.WaitingScreen;
import de.united.azubiware.utility.ClosePopUp;
import de.united.azubiware.utility.Clouds;

public class MainMenuScreen extends ScreenAdapter {

    final AzubiWareGame game;

    private Clouds clouds;
    private MenuButtonManager buttonManager;

    Label label;

    Texture backgroundTexture;
    Sprite backgroundSprite;

    MiniGamePaginator paginator;
    ClosePopUp closePopUp;

    Stage stage;

    public MainMenuScreen(AzubiWareGame game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/backgroundForest.png"));
        backgroundSprite = new Sprite(backgroundTexture);

        createWaitingLabel();
        paginator = new MiniGamePaginator(stage, game.getGameManager());
        buttonManager = new MenuButtonManager(stage, paginator, game);
        clouds = new Clouds(stage);

        closePopUp = new ClosePopUp(stage.getWidth(), stage.getHeight());
        stage.addActor(closePopUp);
        stage.addListener(new ClickListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
                    if(closePopUp.isHidden()){
                        closePopUp.show();
                    }else{
                        closePopUp.hide();
                    }
                }
                return super.keyDown(event, keycode);
            }
        });

        game.getClient().setClientLister(new MenuPacketListener(this));
    }

    public void createWaitingLabel(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getFont();
        labelStyle.fontColor = Color.DARK_GRAY;

        label = new Label("0 players online", labelStyle);
        label.setAlignment(Align.center);
        label.setWidth(300);
        label.setHeight(40);
        label.setPosition(stage.getWidth()/2f-label.getWidth()/2, stage.getHeight()/4.5f-55);
        label.setVisible(false);

        stage.addActor(label);
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
            if(waiting != 0){
                waiting = 0;
            }
        }

        stage.act();
        drawBackground();
        stage.draw();
        clouds.moveClouds();

        paginator.paginate();

        if(foundMatch) {
            dispose();
            game.setScreen(waitingScreen = new WaitingScreen(game, iGame, opponents));
            game.getClient().setMatchListener(iGame.createMatchListener(waitingScreen, game, opponents));
        }
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    long lastUpdated = TimeUtils.millis();
    int waiting = 0;

    boolean foundMatch = false;
    IUser[] opponents;
    WaitingScreen waitingScreen;
    IGame iGame;

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public void updateWaiting(){
        if(TimeUtils.millis()-lastUpdated >= 1000){
            game.getClient().sendQueuePoll(paginator.getCurrentMatchType());
            if(waiting > 1){
                label.setText(waiting + " players online");
            }else{
                label.setText(waiting + " player online");
            }
            lastUpdated = TimeUtils.millis();
        }
    }

    public void startMatch(int matchType, IUser[] oppponents){
        System.out.println("Starting match!");
        foundMatch = true;
        opponents = oppponents;
        if(game.getGameManager().getGameByMatchType(matchType) != null){
            iGame = game.getGameManager().getGameByMatchType(matchType);
        } else{
            System.out.println("Weird stuff");
            foundMatch = false;
        }
    }

}

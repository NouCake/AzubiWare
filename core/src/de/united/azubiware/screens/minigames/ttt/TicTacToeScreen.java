package de.united.azubiware.screens.minigames.ttt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
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
import de.united.azubiware.Games.TTT.TTTPacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.menu.MainMenuScreen;
import de.united.azubiware.screens.minigames.ResultOverlay;
import de.united.azubiware.utility.ClosePopUp;

public class TicTacToeScreen extends ScreenAdapter {

    final AzubiWareGame game;

    private final Sprite backgroundSprite;
    private final Label turn;

    private final Stage stage;
    private final TicTacToeField ticTacToeField;
    private final ClosePopUp closePopUp;
    private final ResultOverlay resultOverlay;

    private boolean yourTurn = false;

    public TicTacToeScreen(AzubiWareGame game, IUser[] opponents){
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("backgrounds/backgroundCastles.png")));

        ticTacToeField = new TicTacToeField(stage);

        Image image = new Image(new Texture(Gdx.files.internal("games/ttt_bottom.png")));
        image.setWidth(stage.getWidth());
        image.setHeight(0.25f * stage.getWidth());
        image.setPosition(stage.getWidth()/2f-image.getWidth()/2f, 0);

        Button leave = new Button(createButtonStyle());
        leave.setPosition(stage.getWidth()/2f - leave.getWidth()/2f, leave.getHeight()/4.25f);
        leave.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!leave.isDisabled() && closePopUp.isHidden()){
                    game.getClient().sendMatchLeave();
                    dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });

        Image top = new Image(new Texture(Gdx.files.internal("games/ttt_top.png")));
        top.setPosition(stage.getWidth()/2-top.getWidth()/2, stage.getHeight()-top.getHeight());

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = game.getFont();
        labelStyle.fontColor =  Color.WHITE;

        turn = new Label("ENEMY TURN", labelStyle);
        turn.setAlignment(Align.center);
        turn.setWidth(stage.getWidth()+1.25f);
        turn.setFontScale(1.15f);
        turn.setPosition(stage.getWidth()/2-turn.getWidth()/2, stage.getHeight()-top.getHeight()/2);

        stage.addActor(image);
        stage.addActor(leave);

        stage.addActor(top);
        stage.addActor(turn);

        resultOverlay = new ResultOverlay(stage);
        closePopUp = new ClosePopUp(stage, game);

        stage.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(closePopUp.isHidden() && yourTurn) {
                    TicTacToePostition postition = ticTacToeField.findPosition(x, y);
                    if (postition != null) {
                        game.getClient().sendMatchPacket(new TTTPacket(postition.getPosX(), postition.getPosY()));
                        postition.setState(1);
                        return true;
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
                    if(!resultOverlay.isShowResult()) {
                        if (closePopUp.isHidden()) {
                            closePopUp.show();
                        } else {
                            closePopUp.hide();
                        }
                    }
                }
                return super.keyDown(event, keycode);
            }
        });
    }

    public void drawBackground(){
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundSprite, 0, 0, stage.getWidth(), stage.getHeight());
        ticTacToeField.draw();
        stage.getBatch().end();
    }

    private Button.ButtonStyle createButtonStyle(){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.over = drawableDown;

        return style;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        drawBackground();
        stage.draw();

        if(resultOverlay.isSwitchToMenu()){
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
        if(yourTurn){
            turn.setText("YOUR TURN");
        }else{
            turn.setText("ENEMY TURN");
        }
    }

    public TicTacToeField getTicTacToeField() {
        return ticTacToeField;
    }

    public ResultOverlay getResultOverlay() {
        return resultOverlay;
    }
}

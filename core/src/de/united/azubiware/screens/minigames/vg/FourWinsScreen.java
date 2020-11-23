package de.united.azubiware.screens.minigames.vg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Packets.TTTPacket;
import de.united.azubiware.Packets.VGPacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.minigames.ResultOverlay;
import de.united.azubiware.screens.minigames.ttt.TicTacToePostition;
import de.united.azubiware.utility.ClickListenerAdapter;
import de.united.azubiware.utility.ClosePopUp;
import de.united.azubiware.utility.IClickListener;

public class FourWinsScreen extends ScreenAdapter {

    private final Stage stage;
    private final AzubiWareGame game;
    private Label turn;
    private final ResultOverlay resultOverlay;
    private final ClosePopUp closePopup;
    private final Button btnLeave;
    private final Batch batch;
    private final FourWinsGrid grid;

    private boolean yourTurn = false;

    public FourWinsScreen(AzubiWareGame game, IUser opponent){
        this.game = game;
        batch = new PolygonSpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        initBackground();
        btnLeave = createLeaveButton();
        turn = createTurnLabel();
        grid = createGrid();
        resultOverlay = new ResultOverlay(stage);
        closePopup = new ClosePopUp(stage, game);

        addCloseListener();
    }

    private Button createLeaveButton(){
        Drawable drawableUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_up.png"))));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/leave/button_leave_down.png"))));

        Button.ButtonStyle style = new Button.ButtonStyle();

        style.up = drawableUp;
        style.down = drawableDown;
        style.over = drawableDown;

        Button btn =  new Button(style);
        btn.setPosition(stage.getWidth()/2f - btn.getWidth()/2f, 10);
        stage.addActor(btn);
        return btn;
    }

    private Label createTurnLabel(){
        if(turn != null) throw new RuntimeException("TurnLabel is already defined!");
        final int padding = 10;
        final float topperScale = 1.1f;

        Image topper = new Image(new Texture(Gdx.files.internal("games/ttt_top.png")));
        topper.setScale(topperScale);
        topper.setPosition(stage.getWidth()*0.5f - topper.getWidth()*0.5f*topperScale, stage.getHeight() - topper.getHeight() * topperScale - padding);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = game.getFont();
        labelStyle.fontColor =  Color.WHITE;

        Label turn = new Label("ENEMY TURN", labelStyle);
        turn.setAlignment(Align.center);
        turn.setWidth(topper.getWidth() * topperScale - 2 * padding);
        turn.setFontScale(1.25f);
        turn.setPosition(topper.getX() + padding, topper.getY() + topper.getHeight()*0.5f*topperScale - turn.getHeight()*0.5f*topperScale);

        stage.addActor(topper);
        stage.addActor(turn);
        return turn;
    }

    private void initBackground(){
        float stageRatio = stage.getWidth() / stage.getHeight();

        Image bg = new Image(new Texture(Gdx.files.internal("backgrounds/backgroundCastles.png")));
        float bgRatio = bg.getWidth() / bg.getHeight();

        System.out.println(stage.getWidth() + " | " + stage.getHeight());
        if(bgRatio > stageRatio){
            float scaling = stage.getHeight() / bg.getHeight();
            bg.setScale(scaling);
            bg.setPosition(stage.getWidth()*0.5f - bg.getWidth()*0.5f*scaling, stage.getHeight()*0.5f - bg.getHeight()*0.5f*scaling);
        } else {
            float scaling = stage.getWidth() / bg.getWidth();
            bg.setScale(scaling);
            bg.setPosition(stage.getWidth()*0.5f - bg.getWidth()*0.5f*scaling, stage.getHeight()*0.5f - bg.getHeight()*0.5f*scaling);
        }

        Image footer = new Image(new Texture(Gdx.files.internal("games/ttt_bottom.png")));
        footer.setScale(stage.getWidth() / footer.getWidth());
        footer.setPosition(0, 0);


        stage.addActor(bg);
        stage.addActor(footer);
    }

    private FourWinsGrid createGrid(){
        FourWinsGrid grid = new FourWinsGrid(){
            @Override
            public void onRowClicked(int row) {
                super.onRowClicked(row);
                FourWinsScreen.this.onRowClicked(row);
            }
        };
        float padding = 50;
        float targetWidth = stage.getWidth() - 2 * padding;
        float scale = targetWidth / grid.getWidth();
        grid.setScale(scale);
        float gridHeight = grid.getHeight() * scale;
        grid.setPosition(stage.getWidth() *0.5f - targetWidth * 0.5f, stage.getHeight() *0.5f - gridHeight * 0.5f);
        stage.addActor(grid);
        return grid;
    }

    private void addCloseListener(){
        stage.addListener(new ClickListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
                    if(!resultOverlay.isShowResult()) {
                        if (closePopup.isHidden()) {
                            closePopup.show();
                        } else {
                            closePopup.hide();
                        }
                    }
                }
                return super.keyDown(event, keycode);
            }
        });
    }

    private void onRowClicked(int row){
        if(closePopup.isHidden() && yourTurn){
            game.getClient().sendMatchPacket(new VGPacket(row));
            grid.addStone(row);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    public void setTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
        grid.setShowHoverStone(yourTurn);
    }

    public void doEnemyTurn(int row) {
        grid.addEnemyStone(row);
    }

    public ResultOverlay getResultOverlay() {
        return resultOverlay;
    }
}

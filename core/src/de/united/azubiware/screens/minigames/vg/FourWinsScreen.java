package de.united.azubiware.screens.minigames.vg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.VG.VGPacket;
import de.united.azubiware.Games.VG.VGTurnHint;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.minigames.MinigameBaseScreen;

public class FourWinsScreen extends MinigameBaseScreen {

    private final int hintUpdateTime = 200;

    private Label turn;
    private final FourWinsGrid grid;

    private long lastHitnSent = System.currentTimeMillis();

    private boolean yourTurn = false;
    private IUser opponent;

    public FourWinsScreen(AzubiWareGame game, IUser opponent){
        super(game, opponent);
        this.opponent = opponent;

        turn = createTurnLabel();
        grid = createGrid();
    }

    private Label createTurnLabel(){
        if(turn != null) throw new RuntimeException("TurnLabel is already defined!");
        final int padding = 10;
        final float topperScale = 1.1f;

        Image topper = new Image(new Texture(Gdx.files.internal("games/ttt_top.png")));
        topper.setScale(topperScale);
        topper.setPosition(getStage().getWidth()*0.5f - topper.getWidth()*0.5f*topperScale, getStage().getHeight() - topper.getHeight() * topperScale - padding);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = getGame().getFont();
        labelStyle.fontColor =  Color.WHITE;

        Label turn = new Label(opponent.getName() + " TURN", labelStyle);
        turn.setAlignment(Align.center);
        turn.setWidth(topper.getWidth() * topperScale - 2 * padding);
        turn.setFontScale(1.25f);
        turn.setPosition(topper.getX() + padding, topper.getY() + topper.getHeight()*0.5f*topperScale - turn.getHeight()*0.5f*topperScale);

        getStage().addActor(topper);
        getStage().addActor(turn);
        return turn;
    }
    private FourWinsGrid createGrid(){
        FourWinsGrid grid = new FourWinsGrid(){
            @Override
            public void onRowClicked(int row) {
                super.onRowClicked(row);
                FourWinsScreen.this.onRowClicked(row);
            }

            @Override
            protected void onHintChanged(int row) {
                super.onHintChanged(row);
                if(System.currentTimeMillis() - lastHitnSent > hintUpdateTime){
                    getGame().getClient().sendMatchPacket(new VGTurnHint(row));
                    lastHitnSent = System.currentTimeMillis();
                }
            }
        };
        float padding = 50;
        float targetWidth = getStage().getWidth() - 2 * padding;
        float scale = targetWidth / grid.getWidth();
        grid.setScale(scale);
        float gridHeight = grid.getHeight() * scale;
        grid.setPosition(getStage().getWidth() *0.5f - targetWidth * 0.5f, getStage().getHeight() *0.5f - gridHeight * 0.5f);
        getStage().addActor(grid);
        return grid;
    }
    private void onRowClicked(int row){
        if(!isInputBlocked() && yourTurn){
            getGame().getClient().sendMatchPacket(new VGPacket(row));
            grid.addStone(row);
            yourTurn = false;
        }
    }


    public void setTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;

        if(yourTurn){
            turn.setText("Your Turn");
        }else{
            turn.setText(opponent.getName() + " Turn");
        }

        grid.setTurn(yourTurn);
    }
    public void doEnemyTurn(int row) {
        grid.addEnemyStone(row);
    }

    public void onHint(int row) {
        grid.setEnemyHint(row);
    }
}

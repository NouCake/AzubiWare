package de.united.azubiware.screens.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;

public class TurnBasedMinigameScreen extends MinigameBaseScreen{

    private Label turn;
    private IUser opponent;
    private boolean yourTurn = false;

    public TurnBasedMinigameScreen(AzubiWareGame game, IUser opponent) {
        super(game, opponent);
        this.opponent = opponent;

        turn = createTurnLabel();
    }

    public TurnBasedMinigameScreen(AzubiWareGame game, IUser opponent, String background) {
        super(game, background, opponent);
        this.opponent = opponent;

        turn = createTurnLabel();
    }

    public void setTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;

        if(yourTurn){
            turn.setText("Your Turn");
        }else{
            turn.setText(opponent.getName() + " Turn");
        }
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

    protected boolean isMyTurn(){
        return yourTurn;
    }

}

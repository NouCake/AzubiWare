package de.united.azubiware.screens.minigames.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;
import de.united.azubiware.utility.topper.PlayerLabel;

public class TurnBasedMinigameScreen extends MinigameBaseScreen{

    private PlayerLabel userLabel;
    private PlayerLabel opponentLabel;

    private Label turn;
    private IUser opponent;
    private boolean yourTurn = false;

    public TurnBasedMinigameScreen(AzubiWareGame game, IUser opponent) {
        this(game, opponent, "Castles");
    }

    public TurnBasedMinigameScreen(AzubiWareGame game, IUser opponent, String background) {
        super(game, background, opponent);
        this.opponent = opponent;

        turn = createTurnLabel();

        createPlayerLabels();
    }

    public void setTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;

        if(yourTurn){
            turn.setText("Your Turn");
        }else{
            turn.setText("Enemy Turn");
        }
    }

    private void createPlayerLabels(){
        final int padding = 10;
        final float topperScale = 1.25f;

        userLabel = new PlayerLabel(getGame().getUser().getName(), "user", getGame().getFont());
        userLabel.setPosition(padding, getStage().getHeight() - userLabel.getHeight() * topperScale - padding);

        opponentLabel = new PlayerLabel(opponent.getName(), "enemy", getGame().getFont());
        opponentLabel.setPosition(getStage().getWidth() - (padding + opponentLabel.getWidth() * 1.25f), getStage().getHeight() - opponentLabel.getHeight() * topperScale - padding);

        getStage().addActor(userLabel);
        getStage().addActor(opponentLabel);
    }

    private Label createTurnLabel(){
        if(turn != null) throw new RuntimeException("TurnLabel is already defined!");

        final int padding = 10;
        final float topperScale = 1.25f;

        Image topper = new Image(new Texture(Gdx.files.internal("games/topper.png")));
        topper.setScale(topperScale);
        topper.setPosition(getStage().getWidth()*0.5f - topper.getWidth()*0.5f*topperScale, getStage().getHeight() - topper.getHeight() * topperScale - padding);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = getGame().getFont();
        labelStyle.fontColor =  Color.WHITE;

        Label turn = new Label("Enemy Turn", labelStyle);
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

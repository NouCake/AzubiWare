package de.united.azubiware.screens.minigames.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;

public class RoundBasedMinigameScreen extends MinigameBaseScreen{

    private IUser[] opponents;
    private Label roundLabel;

    private int currentRound = 1;

    public RoundBasedMinigameScreen(AzubiWareGame game, IUser... opponents) {
        super(game, opponents);

        this.opponents = opponents;

        roundLabel = createRoundLabel();
    }

    public RoundBasedMinigameScreen(AzubiWareGame game, String background, IUser... opponents) {
        super(game, background, opponents);

        this.opponents = opponents;

        roundLabel = createRoundLabel();
    }

    private Label createRoundLabel(){
        if(roundLabel != null) throw new RuntimeException("RoundLabel is already defined!");

        final int padding = 10;
        final float topperScale = 1.1f;

        Image topper = new Image(new Texture(Gdx.files.internal("games/ttt_top.png")));
        topper.setScale(topperScale);
        topper.setPosition(getStage().getWidth()*0.5f - topper.getWidth()*0.5f*topperScale, getStage().getHeight() - topper.getHeight() * topperScale - padding);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = getGame().getFont();
        labelStyle.fontColor =  Color.WHITE;

        Label round = new Label("ROUND " + currentRound, labelStyle);
        round.setAlignment(Align.center);
        round.setWidth(topper.getWidth() * topperScale - 2 * padding);
        round.setFontScale(1.25f);
        round.setPosition(topper.getX() + padding, topper.getY() + topper.getHeight()*0.5f*topperScale - round.getHeight()*0.5f*topperScale);

        getStage().addActor(topper);
        getStage().addActor(round);

        return round;
    }

    public IUser[] getOpponents() {
        return opponents;
    }

    public void setRound(int round){
        currentRound = round;
        roundLabel.setText("ROUND " + currentRound);
    }

    public void doNextRound(){
        setRound(currentRound++);
    }
}

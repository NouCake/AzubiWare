package de.united.azubiware.screens.minigames.ssp;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.minigames.bases.RoundBasedMinigameScreen;
import de.united.azubiware.utility.topper.PlayerLabel;

public class SSPScreen extends RoundBasedMinigameScreen {

    private PlayerLabel userLabel;
    private PlayerLabel opponentLabel;

    private final SSPChooseButtons chooseButtons;
    private final SSPResultImages resultImages;

    private long countdownTime = -1L;
    private int countdown = 5 ;
    private Label countdownLabel;

    private int pickType = 0;

    public SSPScreen(AzubiWareGame game, IUser opponent){
        super(game, "Desert", opponent);

        createPlayerLabels();

        chooseButtons = new SSPChooseButtons(getStage().getWidth(), getStage().getHeight(), this);
        chooseButtons.setPosition(getStage().getWidth()/2, getStage().getHeight()/2, Align.center | Align.bottom);
        getStage().addActor(chooseButtons);

        resultImages = new SSPResultImages(getStage().getWidth(), getStage().getHeight());
        resultImages.setPosition(getStage().getWidth()/2, getStage().getHeight()/2, Align.center | Align.bottom);
        resultImages.setVisible(false);
        getStage().addActor(resultImages);

        countdownLabel = createCountdownLabel();

        reorderOverlays();
    }

    private void createPlayerLabels(){
        final int padding = 10;
        final float topperScale = 1.25f;

        userLabel = new PlayerLabel(getGame().getUser().getName(), "user", getGame().getFont());
        userLabel.setPosition(padding, getStage().getHeight() - userLabel.getHeight() * topperScale - padding);

        opponentLabel = new PlayerLabel(getOpponents()[0].getName(), "enemy", getGame().getFont());
        opponentLabel.setPosition(getStage().getWidth() - (padding + opponentLabel.getWidth() * 1.25f), getStage().getHeight() - opponentLabel.getHeight() * topperScale - padding);

        getStage().addActor(userLabel);
        getStage().addActor(opponentLabel);
    }

    private Label createCountdownLabel(){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = getGame().getFont();
        labelStyle.fontColor =  Color.DARK_GRAY;

        Label turn = new Label(countdown + " seconds left", labelStyle);
        turn.setAlignment(Align.center);
        turn.setWidth(turn.getWidth() * 1.25f - 2 * 10);
        turn.setFontScale(1.25f);
        turn.setPosition(getStage().getWidth()/2 - (turn.getWidth()/2), userLabel.getY()-userLabel.getHeight()-10-turn.getHeight()/2);

        getStage().addActor(turn);

        return turn;
    }

    public void updateCountdown(){
        if(countdown > 0) {
            countdownLabel.setText(countdown + " seconds left");
        }else{
            countdownLabel.setText("Time is over");
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(countdownTime != -1){
            if(countdown > 0){
                if(TimeUtils.millis() - countdownTime >= 1000){
                    countdownTime = TimeUtils.millis();
                    countdown--;
                    updateCountdown();
                }
            }else{
                countdownTime = -1;
                // Send Pick to Server
                chooseButtons.disableButtons();
            }
        }
    }

    public void showRoundResult(int enemyPick, boolean won){
        chooseButtons.setVisible(false);
        resultImages.show(pickType, enemyPick);
        resultImages.setVisible(true);

        if(won){
            countdownLabel.setText("ROUND WON");
        }else{
            countdownLabel.setText("ROUND LOST");
        }
    }

    public void startRound(int round){
        setRound(round);

        countdown = 5;
        countdownTime = TimeUtils.millis();

        resultImages.setVisible(false);
        resultImages.hide();

        chooseButtons.setVisible(true);
        chooseButtons.enableButtons();
    }


    public void setPickType(int pickType) {
        this.pickType = pickType;
    }
}

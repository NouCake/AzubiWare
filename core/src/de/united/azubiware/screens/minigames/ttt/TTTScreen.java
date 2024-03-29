package de.united.azubiware.screens.minigames.ttt;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.Games.TTT.TTTPacket;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.minigames.bases.TurnBasedMinigameScreen;

public class TTTScreen extends TurnBasedMinigameScreen {

    final AzubiWareGame game;

    private final TTTField ticTacToeField;

    private IUser opponent;

    public TTTScreen(AzubiWareGame game, IUser opponent){
        super(game, opponent);

        this.game = game;

        this.opponent = opponent;

        ticTacToeField = new TTTField(getStage());
        getStage().addActor(ticTacToeField);
        getStage().addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(isMyTurn() && ticTacToeField.findPosition(x, y) != null){
                    TTTTPosition position = ticTacToeField.findPosition(x, y);
                    position.setState(1);
                    game.getClient().sendMatchPacket(new TTTPacket(position.getPosX(), position.getPosY()));
                    setTurn(false);
                    return true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        reorderOverlays();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    public TTTField getTicTacToeField() {
        return ticTacToeField;
    }

}

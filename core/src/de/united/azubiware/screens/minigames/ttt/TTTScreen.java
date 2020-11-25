package de.united.azubiware.screens.minigames.ttt;

import de.united.azubiware.AzubiWareGame;
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

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ticTacToeField.draw();
    }

    public TTTField getTicTacToeField() {
        return ticTacToeField;
    }

}

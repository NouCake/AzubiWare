package de.united.azubiware.screens.minigames.ttt;

import de.united.azubiware.AzubiWareGame;
import de.united.azubiware.User.IUser;
import de.united.azubiware.screens.minigames.TurnBasedMinigameScreen;

public class TicTacToeScreen extends TurnBasedMinigameScreen {

    final AzubiWareGame game;

    private final TicTacToeField ticTacToeField;

    private IUser opponent;

    public TicTacToeScreen(AzubiWareGame game, IUser opponent){
        super(game, opponent);

        this.game = game;

        this.opponent = opponent;

        ticTacToeField = new TicTacToeField(getStage());

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ticTacToeField.draw();
    }

    public TicTacToeField getTicTacToeField() {
        return ticTacToeField;
    }

}

package de.united.azubiware.connection.match.sv;

import de.united.azubiware.connection.match.IMatchListener;
import de.united.azubiware.screens.minigames.sv.BattleshipScreen;

public interface IBattleshipListener extends IMatchListener {

    void onOwnturn(int cellX, int cellY, boolean hit);
    void onEnemyTurn(int cellX, int cellY, boolean hit);
    void onSetup(int[][] ships);
    void onNextTurn(boolean yourTurn);

}

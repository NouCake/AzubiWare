package de.united.azubiware.connection.match;

public interface IVGListener extends IMatchListener{

    void onNextTurn(boolean yourTurn);
    void onInvalidTurn();
    void onEnemyTurn(int row);

}

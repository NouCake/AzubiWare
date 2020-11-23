package de.united.azubiware.connection.match;

public interface ITTTListener extends IMatchListener {

    void onNextTurn(boolean yourTurn);
    void onInvalidTurn();
    void onEnemyTurn(int x, int y);

}

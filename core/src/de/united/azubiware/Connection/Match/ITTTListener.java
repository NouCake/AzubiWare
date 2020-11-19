package de.united.azubiware.Connection.Match;

public interface ITTTListener extends IMatchListener {

    void onNextTurn(boolean yourTurn);
    void onInvalidTurn();
    void onEnemyTurn(int x, int y);

}

package de.united.azubiware.connection.match;

public interface ISSPListener extends IMatchListener{

    void onRoundStart(int round);

    void onRoundResult(int enemyPick, int result);

}

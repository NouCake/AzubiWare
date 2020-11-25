package de.united.azubiware.connection.match;

public interface ISSPListener extends IMatchListener{

    void onRoundStart(int round);

    void onRoundOver();

    void onRoundResult(int enemyPick, boolean won);

}

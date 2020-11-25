package de.united.azubiware.connection.match.pong;

import de.united.azubiware.connection.match.IMatchListener;

public interface IPongListener extends IMatchListener {

    void onEnemyMoved(float x);
    void onScoreChanged(int p1, int p2);
    void onBallMoved(float x, float y);

}

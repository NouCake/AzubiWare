package de.united.azubiware.connection.match;

public interface IMatchListener {

    void onMatchReady();
    void onMatchOver(int reason);

}

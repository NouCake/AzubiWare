package de.united.azubiware;

import de.united.azubiware.User.IUserConnection;

public interface ILobbyGame {

    int getMatchType();
    void startMatch(IUserConnection ...users);

    void removeFromQueue(IUserConnection ...connections);
    void addToQueue(IUserConnection connection);
    int getQueueCount();

    boolean isUserCounterValid(int userCount);

}

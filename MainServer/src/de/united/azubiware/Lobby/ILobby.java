package de.united.azubiware.Lobby;

import de.united.azubiware.User.IUserConnection;

public interface ILobby {

    void startQueueing(IUserConnection user, int matchType);
    void stopQueueing(IUserConnection ...user);

    int getUsersInQueue(int matchType);
}

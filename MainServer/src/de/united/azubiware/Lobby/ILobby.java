package de.united.azubiware.Lobby;

import de.united.azubiware.User.IUserConnection;

public interface ILobby {

    void startQueueing(IUserConnection user);
    void stopQueueing(IUserConnection user);

}

package de.united.azubiware.connection.client;

import de.united.azubiware.User.IUser;

public interface IClientListener {

    void onConnected();
    void onError(String messsage);

    void onWelcome(IUser user);
    void onQueueUpdate(int matchType, int usersInQueue);
    void onMatchFound(int matchType, IUser ...oponents);

}

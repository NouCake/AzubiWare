package de.united.azubiware.Connection;

import de.united.azubiware.User.IUser;

public interface IClientListener {

    void onConnected();
    void onError(String messsage);

    void onWelcome(IUser user);
    void onQueueUpdate(int matchType, int usersInQueue);
    void onMatchStart(int matchType, IUser ...oponents);

}

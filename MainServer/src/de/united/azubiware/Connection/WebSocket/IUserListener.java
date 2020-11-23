package de.united.azubiware.Connection.WebSocket;

import de.united.azubiware.User.IUserConnection;

public interface IUserListener {

    void onMessage(IUserConnection user, String message);
    void onLogin(IUserConnection user);
    void onLogout(IUserConnection user);

}

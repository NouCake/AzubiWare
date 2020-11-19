package de.united.azubiware.Connection.WebSocket;

import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.User.IUserConnection;

public interface IUserListener {

    void onPacket(IUserConnection user, IPacket packet);
    void onLogin(IUserConnection user);
    void onLogout(IUserConnection user);

}

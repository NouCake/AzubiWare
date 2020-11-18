package de.united.azubiware.Connection.WebSocket;

import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.User.IUser;

public interface IUserListener {

    void onPacket(IUser user, IPacket packet);
    void onLogin(IUser user);
    void onLogout(IUser user);

}

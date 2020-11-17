package de.united.azubiware.Connection.WebSocket;

import de.united.azubiware.IUser;
import de.united.azubiware.Packets.IPacket;

public interface IUserListener {

    void onPacket(IUser user, IPacket packet);
    void onLogin(IUser user);
    void onLogout(IUser user);

}

package de.united.azubiware.Packets.Handler;


import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.User.IUser;

public interface IPacketHandler {

    void onPacket(IConnection connection, IPacket packet);

}

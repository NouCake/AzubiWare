package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.IPacket;

public interface IConnectionListener {

    void onMessage(IConnection connection, IPacket packet);
    void onConnected(IConnection connection);
    void onClosed(IConnection connection);

}

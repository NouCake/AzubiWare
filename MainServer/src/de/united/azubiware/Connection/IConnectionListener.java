package de.united.azubiware.Connection;

import de.united.azubiware.Packets.IPacket;

public interface IConnectionListener {

    void onMessage(IConnection connection, String message);
    void onConnected(IConnection connection);
    void onClosed(IConnection connection);
    void afterShutdown();

}

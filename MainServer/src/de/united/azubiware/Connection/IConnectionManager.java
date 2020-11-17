package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.IPacket;

public interface IConnectionManager {

    void setConnectionListener(IConnectionListener listener);
    void sendMessage(IConnection connection, IPacket packet);

}

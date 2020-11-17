package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.IPacket;

public interface IConnection {

    void send(IPacket data);

}

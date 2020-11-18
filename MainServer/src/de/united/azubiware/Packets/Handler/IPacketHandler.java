package de.united.azubiware.Packets.Handler;


import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.IPacket;

public interface IPacketHandler {

    void onPacket(IConnection connection, IPacket packet);

}

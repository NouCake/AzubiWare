package de.united.azubiware.Packets.Handler;


import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.IPacket;

public interface IMessageHandler {

    void onMessage(IConnection connection, String message);

}

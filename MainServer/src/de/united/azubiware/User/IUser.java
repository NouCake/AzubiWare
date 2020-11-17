package de.united.azubiware.User;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.IPacket;

import java.util.UUID;

public interface IUser {

    UUID getId();
    String getName();
    IConnection getConnection();
    void send(IPacket packet);

}

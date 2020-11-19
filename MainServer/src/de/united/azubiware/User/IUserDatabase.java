package de.united.azubiware.User;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.LoginPacket;

import java.util.UUID;

public interface IUserDatabase {

    IUserConnection getUserFromUUID(UUID id);
    IUserConnection getUserFromLoginPacket(LoginPacket packet, IConnection connection);

}

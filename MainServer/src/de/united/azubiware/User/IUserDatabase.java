package de.united.azubiware.User;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.LoginPacket;

import java.util.UUID;

public interface IUserDatabase {

    IUser getUserFromUUID(UUID id);
    IUser getUserFromLoginPacket(LoginPacket packet, IConnection connection);

}

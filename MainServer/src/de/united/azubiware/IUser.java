package de.united.azubiware;

import de.united.azubiware.Connection.IConnection;

import java.util.UUID;

public interface IUser {

    UUID getId();
    String getName();
    IConnection getConnection();

}

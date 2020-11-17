package de.united.azubiware;

import de.united.azubiware.Connection.IConnection;

import java.util.UUID;

public class User implements IUser {

    private final UUID id;
    private final String name;
    private final IConnection connection;

    public User( String name, IConnection connection) {
        id = UUID.randomUUID();
        this.name = name;
        this.connection = connection;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IConnection getConnection() {
        return connection;
    }
}

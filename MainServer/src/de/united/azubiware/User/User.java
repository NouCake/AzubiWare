package de.united.azubiware.User;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.IPacket;

import java.util.UUID;

public class User implements IUserConnection {

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

    @Override
    public void send(IPacket packet){
        connection.send(packet);
    }

}

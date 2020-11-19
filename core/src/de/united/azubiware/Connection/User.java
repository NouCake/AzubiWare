package de.united.azubiware.Connection;

import de.united.azubiware.User.IUser;

import java.util.UUID;

public class User implements IUser {

    private final UUID uuid;
    private final String name;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }
}

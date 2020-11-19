package de.united.azubiware.User;

import java.util.UUID;

public class SimpleUser implements IUser{

    private final UUID uuid;
    private final String name;

    public SimpleUser(UUID uuid, String name) {
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

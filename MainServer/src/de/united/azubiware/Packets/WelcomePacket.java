package de.united.azubiware.Packets;

import java.util.UUID;

public class WelcomePacket implements IPacket{

    public static final int type = 7;

    private final UUID uuid;
    private final String username;

    public WelcomePacket(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

}

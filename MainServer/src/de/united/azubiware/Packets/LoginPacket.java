package de.united.azubiware.Packets;

import de.united.azubiware.Packets.Handler.IPacket;

public class LoginPacket implements IPacket {

    public static final int typeID = 3;

    private final String username;

    public LoginPacket(String username) {
        this.username = username;
    }

    @Override
    public Object getData() {
        return null;
    }

    public String getUsername() {
        return username;
    }
}

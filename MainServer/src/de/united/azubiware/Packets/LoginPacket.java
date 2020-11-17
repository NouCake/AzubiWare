package de.united.azubiware.Packets;

public class LoginPacket implements IPacket {

    public static final int type = 1;
    private final String username;

    public LoginPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

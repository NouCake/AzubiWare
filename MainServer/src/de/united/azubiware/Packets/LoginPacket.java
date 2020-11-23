package de.united.azubiware.Packets;

public class LoginPacket implements IPacket {

    private final String username;

    public LoginPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

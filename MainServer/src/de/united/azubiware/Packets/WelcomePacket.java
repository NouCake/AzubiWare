package de.united.azubiware.Packets;

public class WelcomePacket implements IPacket{

    public static final int type = 7;

    private final String username;

    public WelcomePacket(String username) {
        this.username = username;
    }

}

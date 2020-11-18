package de.united.azubiware.Packets;

public class ErrorResponsePacket implements IPacket {

    public static final int type = 3;
    private String error;

    public ErrorResponsePacket(String error) {
        this.error = error;
    }

}

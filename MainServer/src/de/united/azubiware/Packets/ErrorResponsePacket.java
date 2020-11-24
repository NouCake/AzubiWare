package de.united.azubiware.Packets;

public class ErrorResponsePacket implements IPacket {

    private String error;

    public ErrorResponsePacket(String error) {
        this.error = error;
    }

    public String getMessage() {
        return error;
    }
}

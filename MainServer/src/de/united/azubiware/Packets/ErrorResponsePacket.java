package de.united.azubiware.Packets;

public class ErrorResponsePacket implements IPacket {

    private String errorMessage;

    public ErrorResponsePacket(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public int getType() {
        return 127;
    }

    @Override
    public Object getData() {
        return errorMessage;
    }
}

package de.united.azubiware.Packets;

import de.united.azubiware.Packets.Handler.IPacket;

public class ErrorResponsePacket implements IPacket {
    public static final int typeID = 5;

    private String errorMessage;

    public ErrorResponsePacket(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public Object getData() {
        return errorMessage;
    }
}

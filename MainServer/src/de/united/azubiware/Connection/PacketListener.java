package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.IPacketHandler;
import de.united.azubiware.Packets.IPacket;

public class PacketListener implements IConnectionListener{

    private final IPacketHandler handler;

    public PacketListener(IPacketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onMessage(IConnection connection, IPacket packet) {
        handler.onPacket(connection, packet);
    }

    @Override
    public void onConnected(IConnection connection) {

    }

    @Override
    public void onClosed(IConnection connection) {

    }
}

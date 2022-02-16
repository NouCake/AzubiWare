package de.az.ware.connection.packet;

public class DelegatedPacketListener<T> implements PacketListener<T> {

    private final PacketDelegator<T> delegator;

    public DelegatedPacketListener(Class<T> clazz, PacketParser parser, PacketHandler handler) {
        delegator = new PacketDelegator<>(clazz, handler, parser);
    }

    public DelegatedPacketListener(Class<T> clazz, PacketHandler handler) {
        this(clazz, null, handler);
    }

    @Override
    public void onPacket(T connection, Packet packet) {
        delegator.onPacket(connection, packet);
    }

    @Override
    public void onConnected(T connection) {
    }

    @Override
    public void onDisconnected(T connection) {
    }

}

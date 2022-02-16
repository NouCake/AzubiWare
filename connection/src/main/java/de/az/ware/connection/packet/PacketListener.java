package de.az.ware.connection.packet;

public interface PacketListener<T> {

    void onPacket(T connection, Packet packet);
    void onConnected(T connection);
    void onDisconnected(T connection);

}

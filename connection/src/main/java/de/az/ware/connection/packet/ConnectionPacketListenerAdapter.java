package de.az.ware.connection.packet;

import de.az.ware.connection.Connection;
import de.az.ware.connection.ConnectionListener;

public class ConnectionPacketListenerAdapter implements ConnectionListener {

    private final PacketParser parser;
    private final PacketListener<Connection> listener;

    public ConnectionPacketListenerAdapter(PacketParser parser, PacketListener<Connection> listener) {
        this.parser = parser;
        this.listener = listener;
    }

    @Override
    public void onMessage(Connection connection, String message) {
        Packet packet = parser.createPacketFromJson(message);
        if(packet == null) return;

        listener.onPacket(connection, packet);
    }

    @Override
    public void onConnected(Connection connection) {
        listener.onConnected(connection);
    }

    @Override
    public void onDisconnected(Connection connection) {
        listener.onDisconnected(connection);
    }

}

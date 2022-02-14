package connection.packet;

import connection.Connection;

import java.util.HashMap;
import java.util.Map;

public class GenericConnectionAdapter<T> {

    private final Map<Connection, T> connectionToGeneric;
    private final Map<T, Connection> genericToConnection;

    private PacketListener<T> listener;

    public GenericConnectionAdapter(PacketListener<T> listener) {
        this();
        this.listener = listener;
    }

    public GenericConnectionAdapter() {
        connectionToGeneric = new HashMap<>();
        genericToConnection = new HashMap<>();
    }

    public void registerConnection(Connection connection, T player) {
        connectionToGeneric.put(connection, player);
        genericToConnection.put(player, connection);

        if(listener != null) listener.onConnected(player);
    }

    public void send(T generic, String message){
        genericToConnection.get(generic).sendMessage(message);
    }

    public void onPacket(Connection connection, Packet packet){
        T generic = connectionToGeneric.get(connection);
        if(generic == null) return;

        if(listener != null) listener.onPacket(generic, packet);
    }

    public void onDisonnect(Connection connection) {
        T gen = connectionToGeneric.get(connection);
        if(gen == null) return;

        connectionToGeneric.remove(connection);
        genericToConnection.remove(gen);

        if(listener != null) listener.onDisconnected(gen);
    }

    public void setListener(PacketListener<T> listener) {
        this.listener = listener;
    }

}

package match;

import connection.Connection;
import connection.packet.Packet;

import java.util.HashMap;
import java.util.Map;

public class MatchConnectionAdapter {

    private final Map<Connection, MatchPlayer> connectionToPlayer;
    private final Map<MatchPlayer, Connection> playerToConnection;

    private MatchPacketListener listener;

    public MatchConnectionAdapter() {
        connectionToPlayer = new HashMap<>();
        playerToConnection = new HashMap<>();
    }

    public void send(MatchPlayer player, String message){
        playerToConnection.get(player).sendMessage(message);
    }

    public void setListener(MatchPacketListener listener) {
        this.listener = listener;
    }

    public void registerConnection(Connection connection, MatchPlayer player) {
        connectionToPlayer.put(connection, player);
        playerToConnection.put(player, connection);
    }

    public void onPacket(Connection connection, Packet packet) {
        if(listener == null) {
            System.err.println("MatchPacketListenerAdapter is not ready");
            return;
        }

        MatchPlayer player = connectionToPlayer.get(connection);
        if(player == null) {
            System.err.println("Could not handle Packet");
            return;
        }

        listener.onPacket(player, packet);
    }

    public void onDisonnect(Connection connection) {
        MatchPlayer player = connectionToPlayer.get(connection);
        if(player == null) return;

        listener.onDisconnected(player);
    }

}

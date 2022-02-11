package match;

import connection.Connection;
import connection.packet.Packet;
import match.MatchPacketListener;
import match.MatchPlayer;

import java.util.Map;

public class MatchPacketListenerAdapter {

    private Map<Connection, MatchPlayer> map;
    private MatchPacketListener listener;

    public void setListener(MatchPacketListener listener) {
        this.listener = listener;
    }

    public void registerConnection(Connection connection, MatchPlayer player) {
        map.put(connection, player);
    }

    public void onPacket(Connection connection, Packet packet) {
        if(listener == null) {
            System.err.println("MatchPacketListenerAdapter is not ready");
            return;
        }

        MatchPlayer player = map.get(connection);
        if(player == null) {
            System.err.println("Could not handle Packet");
            return;
        }

        listener.onPacket(player, packet);
    }

    public void onDisonnect(Connection connection) {
        MatchPlayer player = map.get(connection);
        if(player == null) {
            System.err.println("Could not handle Packet");
            return;
        }

        listener.onDisconnected(player);
    }

}

package match;

import connection.Connection;
import connection.ConnectionServer;
import connection.packet.Packet;
import connection.packet.PacketHandler;
import connection.packet.PacketHandlerAdapter;
import connection.websocket.WebSocketServerAdapter;
import match.lobby.MatchLobby;
import match.lobby.MatchLobbyListener;
import match.packet.MatchCreationPacket;
import match.packet.MatchLoginPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchServer implements PacketHandler, MatchLobbyListener {

    private Map<MatchLobby, List<Connection>> lobbyConnections;
    private Map<Match, List<Connection>> matchConnections;

    private Map<Connection, MatchPacketListenerAdapter> matchAdapters;

    public MatchServer() {
        ConnectionServer server = new WebSocketServerAdapter(12000);
        server.setConnectionListener(new PacketHandlerAdapter(this){
            @Override
            public void onDisconnected(Connection connection) {
                MatchServer.this.onDisconnected(connection);
            }
        });
    }

    private void onDisconnected(Connection connection) {
        MatchPacketListenerAdapter adapter = matchAdapters.get(connection);
        if(adapter != null) adapter.onDisonnect(connection);

        for(MatchLobby lobby : lobbyConnections.keySet()) {
            if(lobbyConnections.get(lobby).contains(connection)){
                onLobbyCanceled(lobby);
            }
        }
    }

    public void on(Connection connection, MatchCreationPacket packet){
        //TODO: Verify that this can only come from Master Server
        MatchLobby lobby = new MatchLobby(packet, this);
        lobbyConnections.put(lobby, new ArrayList<>());
    }

    public void on(Connection connection, MatchLoginPacket packet){
        lobbyConnections.keySet().forEach(lobby -> {
            MatchPlayer player = lobby.tryLogin(packet);
            if(player != null) {
                lobby.getMatchAdapter().registerConnection(connection, player);
            }
        });
    }

    public void on(Connection connection, Packet packet) {
        MatchPacketListenerAdapter adapter = matchAdapters.get(connection);
        if(adapter != null) adapter.onPacket(connection, packet);
    }

    @Override
    public void onMatchStart(MatchLobby lobby, Match match) {
        List<Connection> connections = lobbyConnections.get(lobby);

        lobbyConnections.remove(lobby);
        matchConnections.put(match, connections);

        MatchPacketListenerAdapter adapter = lobby.getMatchAdapter();
        connections.forEach(c -> matchAdapters.put(c, adapter));
    }

    private void onMatchEnd(Match match){
        List<Connection> connections = matchConnections.get(match);
        connections.forEach(matchAdapters::remove);
    }

    @Override
    public void onLobbyCanceled(MatchLobby lobby) {
        lobbyConnections.remove(lobby);
    }

}

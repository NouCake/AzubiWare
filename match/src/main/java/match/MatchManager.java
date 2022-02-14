package match;

import connection.Connection;
import connection.ConnectionProvider;
import connection.packet.*;
import match.lobby.MatchLobby;
import match.lobby.MatchLobbyListener;
import match.packet.MatchCreationPacket;
import match.packet.MatchLoginPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Listens to Connections and authenticates them.
 * Creates and manages Matches and delegates Connection/Packets to the right Match.
 */
public class MatchManager implements PacketHandler, MatchLobbyListener, MatchListener {

    private final Map<MatchLobby, List<Connection>> lobbyConnections;
    private final Map<Match, List<Connection>> matchConnections;
    private Map<Connection, MatchConnectionAdapter> matchAdapters;

    private final MatchRegistry registry;

    public MatchManager(ConnectionProvider provider, MatchRegistry registry, PacketParser parser) {
        this.registry = registry;
        lobbyConnections = new HashMap<>();
        matchConnections = new HashMap<>();
        matchAdapters = new HashMap<>();

        provider.setConnectionListener(new ConnectionPacketListenerAdapter(parser, new DelegatedPacketListener<>(Connection.class, parser, this){
            @Override
            public void onDisconnected(Connection connection) {
                MatchManager.this.onDisconnected(connection);
            }
        }));
    }

    public void on(Connection connection, MatchCreationPacket packet){
        //TODO: Verify that this can only come from Master Server
        MatchLobby lobby = new MatchLobby(packet, this, registry);
        lobbyConnections.put(lobby, new ArrayList<>());
    }

    public void on(Connection connection, MatchLoginPacket packet){
        for(MatchLobby lobby : lobbyConnections.keySet()) {
            MatchPlayer player = lobby.tryLogin(packet);
            if(player != null) {
                lobbyConnections.get(lobby).add(connection);
                lobby.getMatchAdapter().registerConnection(connection, player);
                lobby.tryStartMatch();
                return;
            }
        };
    }

    public void on(Connection connection, Packet packet) {
        MatchConnectionAdapter adapter = matchAdapters.get(connection);
        if(adapter != null) adapter.onPacket(connection, packet);
    }

    public void onDisconnected(Connection connection) {
        MatchConnectionAdapter adapter = matchAdapters.get(connection);
        if(adapter != null) adapter.onDisonnect(connection);

        for(MatchLobby lobby : lobbyConnections.keySet()) {
            if(lobbyConnections.get(lobby).contains(connection)){
                onLobbyCanceled(lobby);
            }
        }
    }

    @Override
    public void onMatchStart(MatchLobby lobby, Match match) {
        match.setListener(this);

        List<Connection> connections = lobbyConnections.get(lobby);
        lobbyConnections.remove(lobby);
        matchConnections.put(match, connections);

        MatchConnectionAdapter adapter = lobby.getMatchAdapter();
        connections.forEach(c -> matchAdapters.put(c, adapter));
    }


    @Override
    public void onLobbyCanceled(MatchLobby lobby) {
        lobbyConnections.remove(lobby);
    }

    @Override
    public void onMatchOver(Match match) {
        List<Connection> connections = matchConnections.get(match);
        connections.forEach(matchAdapters::remove);
        matchConnections.remove(match);
    }

}

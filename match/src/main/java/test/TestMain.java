package test;

import connection.ConnectionServer;
import connection.packet.PacketParser;
import connection.websocket.WebSocketServerAdapter;
import match.MatchRegistry;
import match.MatchManager;
import ttt.TTTMatch;

public class TestMain {

    public static void main(String[] args) {
        ConnectionServer server = new WebSocketServerAdapter(12000);
        PacketParser parser = new PacketParser();
        MatchRegistry registry = new MatchRegistry(parser);

        registry.registerMatch("ttt", TTTMatch.class, TTTMatch.class);
        MatchManager manager = new MatchManager(server, registry, parser);

        server.start();
    }

}

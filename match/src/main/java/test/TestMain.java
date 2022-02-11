package test;

import connection.ConnectionServer;
import connection.packet.PacketHandlerAdapter;
import connection.packet.PacketParser;
import connection.websocket.WebSocketServerAdapter;
import match.MatchRegistry;
import match.MatchServer;
import ttt.TTTMatch;
import ttt.TTTNextPacket;
import ttt.TTTPacket;

public class TestMain {

    public static void main(String[] args) {

        PacketParser parser = new PacketParser();
        MatchRegistry registry = new MatchRegistry(parser);

        registry.registerMatch("ttt", TTTMatch.class, TTTMatch.class);
        MatchServer matchServer = new MatchServer(registry);

        ConnectionServer server = new WebSocketServerAdapter(12000);
        parser.registerPacketClass(TTTPacket.class);
        parser.registerPacketClass(TTTNextPacket.class);

        server.setConnectionListener(new PacketHandlerAdapter(matchServer, parser));
        server.start();
    }

}

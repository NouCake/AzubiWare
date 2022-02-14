package test;

import connection.Connection;
import connection.ConnectionServer;
import connection.packet.*;
import connection.websocket.WebSocketServerAdapter;

public class OtherTest {

    private static class Player {

    }

    private static class PHandler implements PacketHandler{
        void on(Player p, Packet packet){

        }
    }

    private static class CHandler implements PacketHandler{
        GenericConnectionAdapter<Player> adapter;

        public CHandler(PacketParser parser) {
            adapter = new GenericConnectionAdapter<>(new DelegatedPacketListener<>(Player.class, parser, new PHandler()));
        }

        void on(Connection c, Packet p){
            if(true /*packet is login*/) {
                adapter.registerConnection(c, new Player());
                return;
            }

            adapter.onPacket(c, p);
        }

    }

    public static void main(String[] args) {

        ConnectionServer server = new WebSocketServerAdapter(12000);
        PacketParser parser = new PacketParser();
        server.setConnectionListener(new ConnectionPacketListenerAdapter(parser, new DelegatedPacketListener(Connection.class, null, new CHandler(parser))));


    }

}

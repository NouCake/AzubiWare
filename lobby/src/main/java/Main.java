import connection.Connection;
import connection.ConnectionListener;
import connection.ConnectionServer;
import connection.packet.Packet;
import connection.packet.PacketHandler;
import connection.websocket.WebSocketServerAdapter;

public class Main {

    public static class TestPacket implements Packet {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class Handler implements PacketHandler {

        public void on(Connection connection, TestPacket packet) {
            System.out.println("Got packet: " + packet.name);
        }

    }

    public static void main(String[] args) {

        ConnectionServer server = new WebSocketServerAdapter(12000);
        server.setConnectionListener(new ConnectionListener() {
            @Override
            public void onMessage(Connection connection, String message) {
                System.out.println(Thread.currentThread().getName());
            }

            @Override
            public void onConnected(Connection connection) {
                System.out.println(Thread.currentThread().getName());

            }

            @Override
            public void onDisconnected(Connection connection) {

            }
        });

        System.out.println(Thread.currentThread().getName());
        server.start();
    }

}

package de.united.azubiware;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class Main{

    public static void main(String[] args) {
        System.out.println("Hello World");
        WebSocketServer server = new WebSocketServer(new InetSocketAddress(13030)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                System.out.println("Connected");
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                System.out.println("Closed");
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                System.out.println(message);
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {

            }

            @Override
            public void onStart() {
                System.out.println("Hello, I'm Server.");
            }
        };

        server.start();

    }

}
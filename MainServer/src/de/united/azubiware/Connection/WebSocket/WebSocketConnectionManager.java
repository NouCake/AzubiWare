package de.united.azubiware.Connection.WebSocket;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionListener;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Main;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.Handler.PacketParser;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

public class WebSocketConnectionManager extends WebSocketServer implements IConnectionManager {

    private final int port;

    private IConnectionListener listener;

    private List<WebSocketConnection> connectedSockets;

    public WebSocketConnectionManager(int port) {
        super(new InetSocketAddress(port));
        this.port = port;
        connectedSockets = new LinkedList<>();
    }

    private WebSocketConnection getConnectionFromSocket(WebSocket socket){
        String out = "\n";
        for(int i = 0; i < connectedSockets.size(); i++) {
            WebSocketConnection connection = connectedSockets.get(i);
            if (connection.getSocket() == socket) return connection;
            out += connection + " | " + socket + " [" + i + "] " + connection.getSocket() + "\n";
        }
        System.err.println("Couldn't find Socket: " + socket + out);
        return null;
    }

    // IConnectionManager
    @Override
    public void setConnectionListener(IConnectionListener listener) {
        this.listener = listener;
    }
    @Override
    public void sendMessage(IConnection connection, IPacket packet) {
        WebSocketConnection websocket = (WebSocketConnection) connection;
        try {
            if(websocket.getSocket().isClosing()) throw new WebsocketNotConnectedException();
            String msg = packet.toJsonString();
            websocket.getSocket().send(msg);
            //System.out.println("Sending Message: " + msg);
        } catch (WebsocketNotConnectedException e){
            System.out.println(connection + " | Couldn't send Message: " + packet.toJsonString());
        }
    }
    @Override
    public String getConnectionAdress() {
        return "ws://"+ Main.SERVER_DOMAIN+":"+port;
    }

    // WebSocketServer
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake) {
        WebSocketConnection connection = new WebSocketConnection(this, socket);
        synchronized (connectedSockets){
            connectedSockets.add(connection);
        }

        if(listener != null) listener.onConnected(connection);
    }
    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean remote) {
        WebSocketConnection connection = getConnectionFromSocket(socket);
        synchronized (connectedSockets){
            connectedSockets.remove(connection);
        }
        if(listener != null) listener.onClosed(connection);
//        System.out.println("Closed");
    }
    @Override
    public void onMessage(WebSocket socket, String message) {
        //System.out.println("Got Message: " + message);
        WebSocketConnection connection = getConnectionFromSocket(socket);
        if(listener != null) this.listener.onMessage(connection, message);
    }
    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error with WebSocket");
        ex.printStackTrace();
    }
    @Override
    public void onStart() {
        System.out.println("Started WebSocket Server on Port: " + port);
    }

    @Override
    public void start() {
        super.start();
    }

    private void doStop() throws IOException, InterruptedException {
        super.stop();
    }

    @Override
    public void stop(){
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                doStop();
                if(listener != null) listener.afterShutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}

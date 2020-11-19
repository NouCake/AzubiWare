package de.united.azubiware.Connection.WebSocket;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionListener;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.IPacket;
import de.united.azubiware.Packets.Handler.PacketParser;
import org.java_websocket.WebSocket;
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
        websocket.getSocket().send(packet.toJsonString());
    }

    @Override
    public String getConnectionAdress() {
        return "ws://two.noucake.de:"+port;
    }

    // WebSocketServer
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake) {
        WebSocketConnection connection = new WebSocketConnection(this, socket);
        synchronized (connectedSockets){
            connectedSockets.add(connection);
        }

        if(listener != null) listener.onConnected(connection);
        System.out.println("Connected");
    }
    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean remote) {
        WebSocketConnection connection = getConnectionFromSocket(socket);
        synchronized (connectedSockets){
            connectedSockets.remove(connection);
        }
        if(listener != null) listener.onClosed(connection);
        System.out.println("Closed");
    }
    @Override
    public void onMessage(WebSocket socket, String message) {
        System.out.println("Got Message: " + message);
        WebSocketConnection connection = getConnectionFromSocket(socket);

        IPacket packet = PacketParser.createPacketFromJson(message);
        if(packet == null){
            connection.send(new ErrorResponsePacket("Error while parsing the Package"));
            return;
        }

        if(listener != null) this.listener.onMessage(connection, packet);
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
    public void stop(){
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

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

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

public class WebSocketConnectionManager extends WebSocketServer implements IConnectionManager {

    private final static int PORT = 13000;

    private IConnectionListener listener;

    private List<WebSocketConnection> connectedSockets;

    public WebSocketConnectionManager() {
        super(new InetSocketAddress(PORT));

        connectedSockets = new LinkedList<>();
    }

    private WebSocketConnection getConnectionFromSocket(WebSocket socket){
        for(WebSocketConnection connection : connectedSockets)
            if(connection.getSocket() == socket) return connection;
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

    // WebSocketServer
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake) {
        WebSocketConnection connection = new WebSocketConnection(this, socket);
        connectedSockets.add(connection);

        if(listener != null) listener.onConnected(connection);
        System.out.println("Connected");
    }
    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean remote) {
        WebSocketConnection connection = getConnectionFromSocket(socket);
        connectedSockets.remove(connection);
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
        System.out.println("Started WebSocket Server on Port: " + PORT);
    }

}

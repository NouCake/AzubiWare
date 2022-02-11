package connection.websocket;

import connection.ConnectionListener;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class WebSocketServer extends org.java_websocket.server.WebSocketServer{

    private final List<WebSocketConnection> connectedSockets;
    private final ConnectionListener listener;

    public WebSocketServer(int port, ConnectionListener listener) {
        super(new InetSocketAddress(port));

        if(listener == null) throw new IllegalArgumentException("Listener is null, server is useless.");
        this.listener = listener;

        connectedSockets = new ArrayList<>();
    }

    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake) {
        WebSocketConnection connection = new WebSocketConnection(socket);
        synchronized (connectedSockets) {
            connectedSockets.add(connection);
        }
        listener.onConnected(connection);
    }

    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean remote) {
        WebSocketConnection connection = getConnectionFromSocket(socket);
        synchronized (connectedSockets) {
            connectedSockets.remove(connection);
        }
        if(connection != null) listener.onDisconnected(connection);
        else System.err.println("Server is in an Illegal State. Continueing anyways...");
    }

    @Override
    public void onMessage(WebSocket socket, String message) {
        WebSocketConnection connection = getConnectionFromSocket(socket);

        if(connection == null) {
            System.err.println("Server is in an Illegal State. Continueing anyways...");
            return;
        }

        listener.onMessage(connection, message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {

    }

    @Nullable
    private WebSocketConnection getConnectionFromSocket(WebSocket socket){
        var conn = connectedSockets.stream().filter(connection -> connection.getSocket() == socket).findAny();
        return conn.orElse(null);
    }

}

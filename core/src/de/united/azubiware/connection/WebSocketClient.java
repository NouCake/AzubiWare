package de.united.azubiware.connection;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionListener;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Connection.WebSocket.WebSocketConnection;
import de.united.azubiware.Packets.Handler.PacketParser;
import de.united.azubiware.Packets.IPacket;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketClient extends org.java_websocket.client.WebSocketClient implements IConnectionManager {

    private IConnectionListener listener;
    private WebSocketConnection connection;

    private final String adress;

    public WebSocketClient(URI serverUri) {
        super(serverUri);
        adress = serverUri.toString();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        WebSocket socket = getConnection();
        connection = new WebSocketConnection(this, socket);

        if(listener != null) listener.onConnected(connection);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Got Message: " + message);
        IPacket packet = PacketParser.createPacketFromJson(message);
        if(packet == null) return;

        if(listener != null) listener.onMessage(connection, packet);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if(listener != null) listener.onClosed(connection);
        connection = null;
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error");
        ex.printStackTrace();
    }

    @Override
    public void setConnectionListener(IConnectionListener listener) {
        this.listener = listener;
        System.out.println("Setting Listener!");
    }

    @Override
    public void sendMessage(IConnection connection, IPacket packet) {
        if(!(connection instanceof WebSocketConnection)) throw new RuntimeException("Wrong Connection Type!");
        WebSocketConnection socket = (WebSocketConnection) connection;
        socket.getSocket().send(packet.toJsonString());
    }

    @Override
    public void start() {
        connect();
    }

    @Override
    public void stop() {
        close();
    }

    @Override
    public String getConnectionAdress() {
        return adress;
    }
}

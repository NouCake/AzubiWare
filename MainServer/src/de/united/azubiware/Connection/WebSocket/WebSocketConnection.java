package de.united.azubiware.Connection.WebSocket;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Connection.IConnectionManager;
import de.united.azubiware.Packets.IPacket;
import org.java_websocket.WebSocket;

public class WebSocketConnection implements IConnection {

    private IConnectionManager manager;
    private WebSocket socket;

    public WebSocketConnection(IConnectionManager manager, WebSocket socket) {
        this.manager = manager;
        this.socket = socket;
    }

    @Override
    public void send(IPacket packet) {
        manager.sendMessage(this, packet);
    }

    public WebSocket getSocket() {
        return socket;
    }
}

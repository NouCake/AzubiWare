package connection.websocket;

import connection.Connection;
import org.java_websocket.WebSocket;

public class WebSocketConnection implements Connection {

    private final WebSocket socket;

    public WebSocketConnection(WebSocket socket) {
        this.socket = socket;
    }

    public WebSocket getSocket() {
        return socket;
    }

    @Override
    public void sendMessage(String message) {
        if(!socket.isClosing()) socket.send(message);
    }

}

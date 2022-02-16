package de.az.ware.connection.websocket;

import de.az.ware.connection.ConnectionListener;
import de.az.ware.connection.ConnectionServer;

public class WebSocketServerAdapter implements ConnectionServer {

    private WebSocketServer socketServer;
    private boolean started;
    private int port = 12000;

    public WebSocketServerAdapter(int port) {
        this.port = port;
    }

    @Override
    public void setConnectionListener(ConnectionListener listener) {
        socketServer = new WebSocketServer(port, listener);
    }

    /*
    @Override
    public void sendMessage(Connection connection, String message) {
        connection.sendMessage(message);
    }
    */

    @Override
    public void start() {
        if(started) throw new IllegalStateException("Server was already started!");
        if(socketServer == null) throw new IllegalStateException("Set a Listener first or Server is useless.");

        socketServer.start();
        started = true;
    }

    @Override
    public void stop() {
        if(!started) {
            throw new IllegalStateException("Server is not running!");
        }

        try {
            socketServer.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

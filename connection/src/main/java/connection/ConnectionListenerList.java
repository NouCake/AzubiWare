package connection;

import java.util.ArrayList;
import java.util.List;

public class ConnectionListenerList implements ConnectionListener{

    private final List<ConnectionListener> listener;

    public ConnectionListenerList() {
        listener = new ArrayList<>();
    }

    public void addListener(ConnectionListener listener){
        this.listener.add(listener);
    }

    public void removeListener(ConnectionListener listener){
        this.listener.remove(listener);
    }

    @Override
    public void onMessage(Connection connection, String message) {
        listener.forEach(l -> l.onMessage(connection, message));
    }

    @Override
    public void onConnected(Connection connection) {
        listener.forEach(l -> l.onConnected(connection));
    }

    @Override
    public void onDisconnected(Connection connection) {
        listener.forEach(l -> l.onDisconnected(connection));
    }
}

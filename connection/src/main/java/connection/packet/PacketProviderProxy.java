package connection.packet;

import connection.Connection;
import connection.ConnectionListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Collects all listened events and fires to a specified listener on call.
 * This Class is thread safe and can be used to ensure that all listener methods are called on the same thread.
*/
public class PacketProviderProxy implements ConnectionListener {

    private static <T> void callWithSyncedCopyAndClear(List<T> list, Consumer<T> consumer){
        List<T> copy;
        synchronized (list) {
            copy = List.copyOf(list);
            list.clear();
        }
        copy.forEach(consumer);
    }

    private final List<Connection> connected;
    private final List<Connection> disconnected;
    private final List<Map.Entry<Connection, String>> messages;

    public PacketProviderProxy() {
        connected = new LinkedList<>();
        disconnected = new LinkedList<>();
        messages = new LinkedList<>();
    }


    public void call(ConnectionListener listener){
        callWithSyncedCopyAndClear(connected, listener::onConnected);
        callWithSyncedCopyAndClear(messages, m -> listener.onMessage(m.getKey(), m.getValue()));
        callWithSyncedCopyAndClear(disconnected, listener::onDisconnected);
    }

    @Override
    public void onMessage(Connection connection, String message) {
        messages.add(Map.entry(connection, message));
    }

    @Override
    public void onConnected(Connection connection) {
        synchronized (connected) {
            connected.add(connection);
        }
    }

    @Override
    public void onDisconnected(Connection connection) {
        synchronized (disconnected) {
            disconnected.add(connection);
        }
    }

}

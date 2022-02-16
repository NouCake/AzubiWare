package de.az.ware.connection;

public interface ConnectionListener {

    void onMessage(Connection connection, String message);
    void onConnected(Connection connection);
    void onDisconnected(Connection connection);

}

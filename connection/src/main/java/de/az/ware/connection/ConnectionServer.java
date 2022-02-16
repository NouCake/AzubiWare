package de.az.ware.connection;

public interface ConnectionServer extends ConnectionProvider {

    //void sendMessage(Connection connection, String message);
    void start();
    void stop();

}

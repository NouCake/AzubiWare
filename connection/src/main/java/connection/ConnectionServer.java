package connection;

public interface ConnectionServer {

    void setConnectionListener(ConnectionListener listener);
    //void sendMessage(Connection connection, String message);
    void start();
    void stop();

}

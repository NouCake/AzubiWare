package de.united.azubiware.Connection;

public interface IClient {

    void start();
    void setClientLister(IClientListener lister);
    boolean isConnected();
    void stop();

    void sendLogin(String username);
    void sendQueueStart(int matchType);
    void sendQueueStop();
    void sendQueuePoll(int matchType);

    void setMatchListener(IMatchListener listener);

}

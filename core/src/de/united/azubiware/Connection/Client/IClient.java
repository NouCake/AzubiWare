package de.united.azubiware.Connection.Client;

import de.united.azubiware.Connection.Match.IMatchListener;

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

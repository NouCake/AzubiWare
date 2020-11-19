package de.united.azubiware.connection.client;

import de.united.azubiware.Connection.Match.IMatchListener;
import de.united.azubiware.Packets.IPacket;

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
    void sendMatchPacket(IPacket packet);

}

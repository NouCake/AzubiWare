package de.united.azubiware;

import de.united.azubiware.Matches.IMatch;
import de.united.azubiware.User.IUser;
import de.united.azubiware.User.IUserConnection;

public interface ILobbyGame {

    int getMatchType();
    IMatch startMatch(IUser...users);

    void removeFromQueue(IUserConnection ...connections);
    void addToQueue(IUserConnection connection);
    int getQueueCount();

    boolean isUserCounterValid(int userCount);

}

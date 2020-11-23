package de.united.azubiware.Matches;

import de.united.azubiware.Packets.MatchConnectionInfoPacket;
import de.united.azubiware.User.IUser;

import java.util.UUID;

public interface IMatch {

    int getMatchType();
    IUser[] getUserList();
    void setMatchListener(IMatchListener listener);
    void start();
    MatchConnectionInfoPacket getMatchInfoPacket(UUID user);
    boolean isUserCountValid(int users);

}

package de.united.azubiware.Matches;

import de.united.azubiware.Packets.MatchConnectionInfoPacket;
import de.united.azubiware.User.IUser;

import java.util.List;

public interface IMatch {

    int getMatchType();
    List<IUser> getUserList();
    MatchConnectionInfoPacket getMatchInfoPacket();
    void setMatchListener(IMatchListener listener);

}

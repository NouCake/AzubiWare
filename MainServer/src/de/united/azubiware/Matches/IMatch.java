package de.united.azubiware.Matches;

import de.united.azubiware.IUser;
import de.united.azubiware.Packets.MatchInfoPacket;

import java.util.List;

public interface IMatch {

    int getMatchType();
    List<IUser> getUserList();
    MatchInfoPacket getMatchInfoPacket();

}

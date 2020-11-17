package de.united.azubiware.Matches;

import de.united.azubiware.IUser;

import java.util.List;

public interface IMatch {

    int getMatchType();
    List<IUser> getUserList();

    void abortMatch();

}

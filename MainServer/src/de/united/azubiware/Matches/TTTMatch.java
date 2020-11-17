package de.united.azubiware.Matches;

import de.united.azubiware.IUser;

import java.util.LinkedList;
import java.util.List;

public class TTTMatch implements IMatch {

    private final static int MATCH_ID = 1;

    private IUser u1;
    private IUser u2;
    private final List<IUser> users;

    public TTTMatch(IUser u1, IUser u2) {
        this.u1 = u1;
        this.u2 = u2;

        users = new LinkedList<>();
        users.add(u1);
        users.add(u2);
    }

    @Override
    public int getMatchType() {
        return MATCH_ID;
    }

    @Override
    public List<IUser> getUserList() {
        return users;
    }

    @Override
    public void abortMatch() {

    }
}

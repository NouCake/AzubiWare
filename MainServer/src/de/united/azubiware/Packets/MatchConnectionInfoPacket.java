package de.united.azubiware.Packets;

import de.united.azubiware.User.IUser;
import de.united.azubiware.User.SimpleUser;

import java.util.UUID;

public class MatchConnectionInfoPacket implements IPacket {

    public static final int type = 2;
    private final int matchtype;

    private final String adress;
    private final UUID matchToken;

    private final IUser[] oponents;

    public MatchConnectionInfoPacket(int matchtype, String adress, UUID matchToken, IUser[] oponents) {
        this.matchtype = matchtype;
        this.adress = adress;
        this.matchToken = matchToken;
        this.oponents = oponents;
    }

    public int getMatchtype() {
        return matchtype;
    }

    public String getAdress() {
        return adress;
    }

    public UUID getMatchToken() {
        return matchToken;
    }

    public IUser[] getOponents() {
        return oponents;
    }
}

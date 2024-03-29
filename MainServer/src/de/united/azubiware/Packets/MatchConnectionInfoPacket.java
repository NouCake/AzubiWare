package de.united.azubiware.Packets;

import de.united.azubiware.User.IUser;
import de.united.azubiware.User.SimpleUser;

import java.util.Arrays;
import java.util.UUID;

public class MatchConnectionInfoPacket implements IPacket {

    private final int matchtype;

    private final String adress;
    private final UUID matchToken;

    private final SimpleUser[] oponents;

    public MatchConnectionInfoPacket(int matchtype, String adress, UUID matchToken, IUser[] oponents) {
        this(matchtype, adress, matchToken, Arrays.stream(oponents).map(SimpleUser::new).toArray(SimpleUser[]::new));
    }

    public MatchConnectionInfoPacket(int matchtype, String adress, UUID matchToken, SimpleUser[] oponents) {
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

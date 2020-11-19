package de.united.azubiware.Packets;

import java.util.UUID;

public class MatchConnectionInfoPacket implements IPacket {

    public static final int type = 2;
    private final int matchtype;
    private final String adress;
    private final UUID matchToken;

    public MatchConnectionInfoPacket(int matchtype, String adress, UUID matchToken) {
        this.matchtype = matchtype;
        this.adress = adress;
        this.matchToken = matchToken;
    }
}

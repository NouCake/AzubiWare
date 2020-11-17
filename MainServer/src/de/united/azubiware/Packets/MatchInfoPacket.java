package de.united.azubiware.Packets;

import de.united.azubiware.Packets.Handler.IPacket;

public class MatchInfoPacket implements IPacket {

    int typeID = 2;

    private final int matchtype;
    private final String adress;

    public MatchInfoPacket(int matchtype, String adress) {
        this.matchtype = matchtype;
        this.adress = adress;
    }

    @Override
    public Object getData() {
        return null;
    }
}

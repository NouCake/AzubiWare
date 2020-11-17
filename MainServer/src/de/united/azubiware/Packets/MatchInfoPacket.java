package de.united.azubiware.Packets;

public class MatchInfoPacket implements IPacket {

    public static final int type = 2;
    private final int matchtype;
    private final String adress;

    public MatchInfoPacket(int matchtype, String adress) {
        this.matchtype = matchtype;
        this.adress = adress;
    }
}

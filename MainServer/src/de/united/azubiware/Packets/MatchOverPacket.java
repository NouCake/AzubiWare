package de.united.azubiware.Packets;

public class MatchOverPacket implements IPacket {

    public enum REASONS{
        YOU_WON,
        YOU_LOST,
        DRAW,
        GAME_DONE,
        ABORTED
    }

    public static final int type = 16;
    private final int reason;

    public MatchOverPacket(int reason) {
        this.reason = reason;
    }

    public MatchOverPacket(REASONS reason){
        this.reason = reason.ordinal();
    }

    public int getReason() {
        return reason;
    }
}

package match.packet;

import connection.packet.Packet;

public class MatchCreationPacket implements Packet {

    private final String matchType;
    private final String[] playerMatchTokens;

    public MatchCreationPacket(String matchType, String[] playerMatchTokens) {
        this.matchType = matchType;
        this.playerMatchTokens = playerMatchTokens;
    }

    public String getMatchType() {
        return matchType;
    }

    public String[] getPlayerMatchTokens() {
        return playerMatchTokens;
    }

}

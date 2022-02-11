package match.packet;

public class MatchCreationPacket {

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

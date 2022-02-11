package match.packet;

public class MatchLoginPacket {

    private final String matchtoken;

    public MatchLoginPacket(String matchtoken) {
        this.matchtoken = matchtoken;
    }

    public String getMatchtoken() {
        return matchtoken;
    }

}

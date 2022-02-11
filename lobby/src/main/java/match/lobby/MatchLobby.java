package match.lobby;

import match.Match;
import match.MatchPacketHandlerAdapter;
import match.MatchPacketListenerAdapter;
import match.MatchPlayer;
import match.packet.MatchCreationPacket;
import match.packet.MatchLoginPacket;
import ttt.TTTMatch;

import java.util.ArrayList;
import java.util.List;

public class MatchLobby {

    private final String matchType;
    private final List<MatchPlayer> connectedPlayers;

    private List<String> playerMatchToken;
    private final MatchPacketListenerAdapter matchAdapter;

    private final MatchLobbyListener listener;

    public MatchLobby(MatchCreationPacket packet, MatchLobbyListener listener) {
        if(packet.getPlayerMatchTokens() == null || packet.getPlayerMatchTokens().length == 0) {
            throw new IllegalArgumentException("No/not enough players for match");
        }
        if(listener == null){
            throw new IllegalArgumentException("MatchLobbyListener is null");
        }
        this.listener = listener;
        this.matchType = packet.getMatchType();
        this.playerMatchToken = List.of(packet.getPlayerMatchTokens());

        connectedPlayers = new ArrayList<>(playerMatchToken.size());
        matchAdapter = new MatchPacketListenerAdapter();
    }

    private void startMatch(){
        if(matchType.equals("ttt")) {
            Match match = new TTTMatch(connectedPlayers.toArray(new MatchPlayer[0]));
            matchAdapter.setListener(new MatchPacketHandlerAdapter(match.getPacketHandler()));

            listener.onMatchStart(this, match);
        }
    }

    public MatchPacketListenerAdapter getMatchAdapter() {
        return matchAdapter;
    }

    public MatchPlayer tryLogin(MatchLoginPacket packet){
        if(!playerMatchToken.contains(packet.getMatchtoken())) return null;

        MatchPlayer player = new MatchPlayer(connectedPlayers.size());
        connectedPlayers.add(player);

        playerMatchToken.remove(packet.getMatchtoken());
        if(playerMatchToken.size() <= 0) startMatch();

        return player;
    }

}

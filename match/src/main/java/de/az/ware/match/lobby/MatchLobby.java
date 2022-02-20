package de.az.ware.match.lobby;

import de.az.ware.match.Match;
import de.az.ware.match.MatchConnectionAdapter;
import de.az.ware.match.MatchPlayer;
import de.az.ware.match.MatchRegistry;
import de.az.ware.common.packets.MatchCreation;
import de.az.ware.common.packets.MatchLogin;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for authenticating Player and creating/starting the corresponding Match when all Players are gathered.
 */
public class MatchLobby {

    private final String matchType;
    private final List<MatchPlayer> connectedPlayers;

    private List<String> playerMatchToken;
    private final MatchConnectionAdapter matchAdapter;

    private final MatchLobbyListener listener;
    private final MatchRegistry registry;

    public MatchLobby(MatchCreation.Request packet, MatchLobbyListener listener, MatchRegistry registry) {
        if(packet.getPlayerMatchTokens() == null || packet.getPlayerMatchTokens().length == 0) {
            throw new IllegalArgumentException("No/not enough players for match");
        }
        if(listener == null){
            throw new IllegalArgumentException("MatchLobbyListener is null");
        }
        this.registry = registry;
        this.listener = listener;
        this.matchType = packet.getMatchType();
        this.playerMatchToken = new ArrayList<>();
        playerMatchToken.addAll(List.of(packet.getPlayerMatchTokens()));

        connectedPlayers = new ArrayList<>(playerMatchToken.size());
        matchAdapter = new MatchConnectionAdapter();
    }

    private void startMatch(){
        Match match = registry.createMatch(matchType, matchAdapter, connectedPlayers.toArray(new MatchPlayer[0]));
        listener.onMatchStart(this, match);
    }

    public MatchConnectionAdapter getMatchAdapter() {
        return matchAdapter;
    }

    public MatchPlayer tryLogin(MatchLogin.Request packet){
        if(!playerMatchToken.contains(packet.getMatchtoken())) return null;

        MatchPlayer player = new MatchPlayer(connectedPlayers.size());
        connectedPlayers.add(player);

        playerMatchToken.remove(packet.getMatchtoken());

        return player;
    }

    public void tryStartMatch(){
        if(playerMatchToken.size() <= 0) startMatch();
    }

}

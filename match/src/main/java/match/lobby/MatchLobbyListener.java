package match.lobby;

import match.Match;

public interface MatchLobbyListener {

    void onMatchStart(MatchLobby lobby, Match match);
    void onLobbyCanceled(MatchLobby lobby);

}

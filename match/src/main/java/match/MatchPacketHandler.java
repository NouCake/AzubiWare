package match;

public interface MatchPacketHandler {

    default void onDisconnected(MatchPlayer player){
        if(this instanceof Match) {
            Match match = (Match)this;
            match.onPlayerDisconnect(player);
        }
    }

}

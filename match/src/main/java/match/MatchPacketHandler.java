package match;

/**
 * Should be implemented by a class that implements Handler Methods.
 * Handler Methods are Methods that take 2 parameter: (MatchPlayer, ? extends Packet)
 * Will be used by a MatchPacketHandlerAdapter to find and call the right Handler Method
 */
public interface MatchPacketHandler {

    default void onDisconnected(MatchPlayer player){
        if(this instanceof Match) {
            Match match = (Match)this;
            match.onPlayerDisconnect(player);
        }
    }

}

package match;

import connection.packet.Packet;

/**
 * A Listener class for Connection Events for Matches.
 * Doesn't need a onConnect method, since MatchPlayer are already connected before Match starts/exists.
 */
public interface MatchPacketListener {

    void onPacket(MatchPlayer player, Packet packet);
    void onDisconnected(MatchPlayer player);

}

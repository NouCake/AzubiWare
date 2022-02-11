package match;

import connection.Connection;
import connection.packet.Packet;

public interface MatchPacketListener {

    void onPacket(MatchPlayer player, Packet packet);
    void onDisconnected(MatchPlayer connection);

}

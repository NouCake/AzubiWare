package connection.packet;

import connection.Connection;
import connection.ConnectionListener;

public interface PacketHandler extends ConnectionListener {

    @Override
    default void onMessage(Connection connection, String message) { }

}

package connection.packet;

/**
 * Should be implemented by a class that implements Handler Methods.
 * Handler Methods are Methods that take 2 parameter: (Connection, ? extends Packet)
 * Will be used by a PacketHandlerAdapter to find and call the right Handler Method
 */
public interface PacketHandler {

}

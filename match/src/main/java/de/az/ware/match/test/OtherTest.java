package de.az.ware.match.test;

import de.az.ware.connection.Connection;
import de.az.ware.connection.packet.*;

public class OtherTest {

    private static class Player {

    }

    private static class PHandler implements PacketHandler {
        void on(Player p, Packet packet){

        }
    }

    private static class CHandler implements PacketHandler{
        GenericConnectionAdapter<Player> adapter;

        public CHandler(PacketParser parser) {
            adapter = new GenericConnectionAdapter<>(new DelegatedPacketListener<>(Player.class, parser, new PHandler()));
        }

        void on(Connection c, Packet p){
            if(true /*packet is login*/) {
                adapter.registerConnection(c, new Player());
                return;
            }

            adapter.onPacket(c, p);
        }

    }

    public static void main(String[] args) {
        /*

        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        while(it.hasNext()){
            String s = it.next();
            if(s.equals("three")) it.remove();
            System.out.println(s);
        }

        for (Iterator<String> iter = list.iterator(); iter.hasNext(); ) {
            String s = iter.next();
        }

*/
    }
}

package de.az.ware.match.test;

import de.az.ware.connection.Connection;
import de.az.ware.connection.packet.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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


        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        list.forEach(l -> {
            if(l.equals("one")) list.remove(l);
        });

    }
}

package de.az.ware.common.packets;

import de.az.ware.connection.packet.Packet;

public class MatchLogin {

    public static class Request implements Packet{

        public String matchtoken;

        public Request(String matchtoken) {
            this.matchtoken = matchtoken;
        }

        public Request() {
        }

    }


}

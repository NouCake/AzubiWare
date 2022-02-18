package de.az.ware.common.packets;

import de.az.ware.connection.packet.Packet;

public class MatchLoginPacket  {

    public static class Request implements Packet{

        private final String matchtoken;

        public Request(String matchtoken) {
            this.matchtoken = matchtoken;
        }

        public String getMatchtoken() {
            return matchtoken;
        }
    }


}

package de.az.ware.common.packets;

import de.az.ware.connection.packet.Packet;

public class MatchLogin {

    public static class Request implements Packet{

        private String matchtoken;

        public Request(String matchtoken) {
            this.matchtoken = matchtoken;
        }

        public Request() {
        }

        public String getMatchtoken() {
            return matchtoken;
        }

        public void setMatchtoken(String matchtoken) {
            this.matchtoken = matchtoken;
        }
    }


}

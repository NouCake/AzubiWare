package de.az.ware.common.packets;

import de.az.ware.connection.packet.Packet;

public class MatchCreation {

    public static class Request implements Packet {
        public String matchType;
        public String[] playerMatchTokens;

        public Request(String matchType, String[] playerMatchTokens) {
            this.matchType = matchType;
            this.playerMatchTokens = playerMatchTokens;
        }

        public Request() {
        }
    }

}

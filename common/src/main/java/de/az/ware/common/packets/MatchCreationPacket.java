package de.az.ware.common.packets;

import de.az.ware.connection.packet.Packet;

public class MatchCreationPacket {

    public static class Request implements Packet {
        private final String matchType;
        private final String[] playerMatchTokens;

        public Request(String matchType, String[] playerMatchTokens) {
            this.matchType = matchType;
            this.playerMatchTokens = playerMatchTokens;
        }

        public String getMatchType() {
            return matchType;
        }

        public String[] getPlayerMatchTokens() {
            return playerMatchTokens;
        }
    }

}

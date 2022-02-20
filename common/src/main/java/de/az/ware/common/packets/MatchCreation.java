package de.az.ware.common.packets;

import de.az.ware.connection.packet.Packet;

public class MatchCreation {

    public static class Request implements Packet {
        private String matchType;
        private String[] playerMatchTokens;

        public Request(String matchType, String[] playerMatchTokens) {
            this.matchType = matchType;
            this.playerMatchTokens = playerMatchTokens;
        }

        public Request() {
        }

        public String getMatchType() {
            return matchType;
        }

        public void setMatchType(String matchType) {
            this.matchType = matchType;
        }

        public String[] getPlayerMatchTokens() {
            return playerMatchTokens;
        }

        public void setPlayerMatchTokens(String[] playerMatchTokens) {
            this.playerMatchTokens = playerMatchTokens;
        }
    }

}

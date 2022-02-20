package de.az.ware.common.packets;

import de.az.ware.common.model.MatchType;

import java.util.Map;

public class LobbyQueuePoll {

    public static class Request {

    }

    public static class Response {

        private Map<MatchType, Integer> queueLengths;

        public Response(Map<MatchType, Integer> queueLengths) {
            this.queueLengths = queueLengths;
        }

        public Response() {
        }

        public Map<MatchType, Integer> getQueueLengths() {
            return queueLengths;
        }

        public void setQueueLengths(Map<MatchType, Integer> queueLengths) {
            this.queueLengths = queueLengths;
        }
    }

}

package de.az.ware.common.packets;

public class MatchQueuePoll {

    public static class Request {
        private final String matchType;

        public Request(String matchType) {
            this.matchType = matchType;
        }

        public String getMatchType() {
            return matchType;
        }
    }

    public static class Response {
        private final int peopleInQueue;

        public Response(int peopleInQueue) {
            this.peopleInQueue = peopleInQueue;
        }

        public int getPeopleInQueue() {
            return peopleInQueue;
        }
    }

}

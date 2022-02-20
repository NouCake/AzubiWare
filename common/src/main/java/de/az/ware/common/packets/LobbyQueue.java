package de.az.ware.common.packets;

import de.az.ware.common.model.MatchType;

import javax.validation.constraints.NotNull;

public class LobbyQueue {

    public enum Status {
        OK,
        QUEUE_IS_FULL,
        MATCH_FOUND
    }

    public static class Request {

        @NotNull
        private MatchType queue;

        public Request() {
        }

        public Request(MatchType queue) {
            this.queue = queue;
        }

        public MatchType getQueue() {
            return queue;
        }

        public void setQueue(MatchType queue) {
            this.queue = queue;
        }
    }

    public static class Response{
        private Status status;

        public Response() {

        }

        public Response(Status status) {
            this.status = status;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }

}

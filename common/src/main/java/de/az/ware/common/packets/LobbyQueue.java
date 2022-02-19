package de.az.ware.common.packets;

import de.az.ware.common.model.MatchType;
import jakarta.validation.constraints.NotNull;

public class LobbyQueue {

    public static class Request {

        @NotNull
        public MatchType queue;

        public Request() {
        }

        public Request(MatchType queue) {
            this.queue = queue;
        }
    }

    public static class Response{

    }

}

package de.az.ware.common.packets;

import de.az.ware.common.model.LobbyUser;

public class LobbyLogin {

    public enum Status {
        OK,
        NOT_REGISTERED
    }

    public static class Request {

    }

    public static class Response {
        public Status status;
        public LobbyUser user;

        public Response(Status status, LobbyUser user) {
            this.status = status;
            this.user = user;
        }

        public Response() {
        }
    }

}

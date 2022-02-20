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
        private Status status;
        private LobbyUser user;

        public Response(Status status, LobbyUser user) {
            this.status = status;
            this.user = user;
        }

        public Response() {
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public LobbyUser getUser() {
            return user;
        }

        public void setUser(LobbyUser user) {
            this.user = user;
        }
    }

}

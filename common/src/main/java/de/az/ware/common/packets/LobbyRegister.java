package de.az.ware.common.packets;

import de.az.ware.common.model.LobbyUser;
import javax.validation.constraints.Size;

public class LobbyRegister {

    public enum Status {
        OK,
        USERNAME_ALREADY_USED,
        //GOOGLE_ID_ALREADY_USED,   => same as ALREADY_REGISTERED
        ALREADY_REGISTERED
    }

    public static class Request {

        @Size(min = 3, message = "username has to be > 3 characters")
        @Size(max = 20, message = "username has to be < 20 characters")
        private String username;

        public Request(String username) {
            this.username = username;
        }

        public Request() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
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

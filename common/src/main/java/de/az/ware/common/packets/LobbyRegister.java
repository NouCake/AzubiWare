package de.az.ware.common.packets;

import de.az.ware.common.model.LobbyUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LobbyRegister {

    public enum Status {
        OK,
        USERNAME_ALREADY_USED,
        //GOOGLE_ID_ALREADY_USED,   => same as ALREADY_REGISTERED
        ALREADY_REGISTERED
    }

    public static class Request {

        @NotBlank(message = "username is mandatory")
        @Size(min = 3, message = "username has to be > 3 characters")
        @Size(max = 20, message = "username has to be < 20 characters")
        public String username;

        public Request(String username) {
            this.username = username;
        }

        public Request() {
        }
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

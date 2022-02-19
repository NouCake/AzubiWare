package de.az.ware.lobby;

import javax.validation.constraints.Size;

public class TestReq {

    @Size(min = 3, max = 20, message = "length not ok")
    public String username;

}

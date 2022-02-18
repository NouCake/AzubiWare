package de.az.ware.lobby;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LobbyController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello!";
    }

}

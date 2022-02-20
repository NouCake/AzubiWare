package de.az.ware.lobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("de.az.ware.common.*") //Needed for JPA Stuff
@EnableScheduling //For queueing in LobbyController
public class LobbyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LobbyApplication.class, args);
    }

}

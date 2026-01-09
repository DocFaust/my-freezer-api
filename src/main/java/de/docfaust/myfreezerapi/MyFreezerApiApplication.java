package de.docfaust.myfreezerapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MyFreezerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFreezerApiApplication.class, args);
        log.info("MyFreezerApi application started.");
    }

}

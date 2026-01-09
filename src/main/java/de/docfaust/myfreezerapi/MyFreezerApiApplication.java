package de.docfaust.myfreezerapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the MyFreezer API Spring Boot application.
 */
@SpringBootApplication
@Slf4j
public class MyFreezerApiApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(MyFreezerApiApplication.class, args);
        log.info("MyFreezerApi application started.");
    }

}

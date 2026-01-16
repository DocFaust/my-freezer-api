package de.docfaust.myfreezerapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the MyFreezer API Spring Boot application.
 */
@SuppressWarnings("java:S1118")
@SpringBootApplication
@Slf4j
public final class MyFreezerApiApplication {

    /**
     * Private constructor to prevent instantiation.
     */
    private MyFreezerApiApplication() {
        // Utility class constructor
    }

    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(final String[] args) {
        SpringApplication.run(MyFreezerApiApplication.class, args);
        log.info("MyFreezerApi application started.");
    }

}

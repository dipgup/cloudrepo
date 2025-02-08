package com.dg;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GoalTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoalTrackerApplication.class, args);
    }

    @PreDestroy
    public void onShutdown() {
        log.info("🚀 Application is shutting down... Cleaning up resources.");
        // Add cleanup logic here (e.g., closing DB connections, stopping threads)
    }

    @PostConstruct
    public void attachShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("⚠️ JVM Shutdown Hook triggered! Cleaning up resources...");
            // Perform cleanup tasks (e.g., closing files, stopping external processes)
        }));
    }
}
package com.kraya.platform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * HealthController provides health check endpoints for monitoring the application.
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    /**
     * Health check endpoint to verify the application is running.
     *
     * @return ResponseEntity containing health status
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Kraya Platform API");
        health.put("version", "1.0.0");

        return ResponseEntity.ok(health);
    }
}

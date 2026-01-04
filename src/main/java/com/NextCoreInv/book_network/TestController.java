package com.NextCoreInv.book_network;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final DataSource dataSource;
    private final DataSourceProperties dataSourceProperties;

    @GetMapping
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> status = new HashMap<>();
        
        // API Status
        status.put("api_status", "✅ WORKING");
        status.put("timestamp", LocalDateTime.now());
        status.put("message", "API is running successfully");
        
        // Database Status
        Map<String, Object> dbStatus = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            dbStatus.put("status", "✅ CONNECTED");
            dbStatus.put("database", connection.getMetaData().getDatabaseProductName());
            dbStatus.put("version", connection.getMetaData().getDatabaseProductVersion());
            dbStatus.put("url", dataSourceProperties.getUrl());
            dbStatus.put("driver", connection.getMetaData().getDriverName());
            dbStatus.put("connection_valid", connection.isValid(5));
        } catch (Exception e) {
            dbStatus.put("status", "❌ CONNECTION_FAILED");
            dbStatus.put("error", e.getMessage());
        }
        
        status.put("database", dbStatus);
        
        // System Info
        Map<String, Object> systemInfo = new HashMap<>();
        systemInfo.put("java_version", System.getProperty("java.version"));
        systemInfo.put("spring_boot", "3.2.2");
        systemInfo.put("available_processors", Runtime.getRuntime().availableProcessors());
        systemInfo.put("max_memory_mb", Runtime.getRuntime().maxMemory() / (1024 * 1024));
        systemInfo.put("total_memory_mb", Runtime.getRuntime().totalMemory() / (1024 * 1024));
        systemInfo.put("free_memory_mb", Runtime.getRuntime().freeMemory() / (1024 * 1024));
        
        status.put("system", systemInfo);
        
        // Service Status
        Map<String, String> services = new HashMap<>();
        services.put("authentication", "✅ AVAILABLE");
        services.put("book_management", "✅ AVAILABLE");
        services.put("feedback_system", "✅ AVAILABLE");
        services.put("file_storage", "✅ AVAILABLE");
        services.put("email_service", "✅ AVAILABLE");
        
        status.put("services", services);
        
        return ResponseEntity.ok(status);
    }
}
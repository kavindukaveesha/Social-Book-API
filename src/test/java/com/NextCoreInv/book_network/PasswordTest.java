package com.NextCoreInv.book_network;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    
    @Test
    public void testPasswordEncoding() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password123";
        String storedHash = "$2a$10$TKh8H1.PfQx37YgCzwiKb.KjNyTpT8thRIXTXaUf5kVfgwRk6eoW6";
        
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Stored hash: " + storedHash);
        System.out.println("Password matches: " + encoder.matches(rawPassword, storedHash));
        
        // Generate a new hash for verification
        String newHash = encoder.encode(rawPassword);
        System.out.println("New hash: " + newHash);
        System.out.println("New hash matches: " + encoder.matches(rawPassword, newHash));
    }
}
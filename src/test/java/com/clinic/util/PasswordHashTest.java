package com.clinic.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordHashTest {

    @Test
    public void testAdminPasswordHash() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // 既存のハッシュ
        String existingHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ/6";
        
        // admin123で検証
        boolean matches = encoder.matches("admin123", existingHash);
        System.out.println("Existing hash matches 'admin123': " + matches);
        
        // 新しいハッシュを生成
        String newHash = encoder.encode("admin123");
        System.out.println("New hash for 'admin123': " + newHash);
        
        // 新しいハッシュで検証
        boolean newMatches = encoder.matches("admin123", newHash);
        System.out.println("New hash matches 'admin123': " + newMatches);
        
        assertTrue(newMatches, "新しいハッシュはadmin123と一致する必要があります");
    }
}


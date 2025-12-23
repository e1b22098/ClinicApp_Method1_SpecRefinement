package com.clinic.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * パスワードハッシュ生成用ユーティリティ
 * このクラスは開発・デバッグ用です
 */
public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // admin123のハッシュを生成
        String adminPassword = "admin123";
        String adminHash = encoder.encode(adminPassword);
        System.out.println("Admin password: " + adminPassword);
        System.out.println("Admin hash: " + adminHash);
        
        // 検証
        boolean matches = encoder.matches(adminPassword, adminHash);
        System.out.println("Password matches: " + matches);
        
        // 既存のハッシュで検証
        String existingHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ/6";
        boolean existingMatches = encoder.matches(adminPassword, existingHash);
        System.out.println("Existing hash matches admin123: " + existingMatches);
    }
}


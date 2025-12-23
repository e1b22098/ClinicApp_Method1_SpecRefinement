package com.clinic.config;

import com.clinic.repository.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * アプリケーション起動時に管理者パスワードを初期化
 * 開発・デバッグ用
 */
@Component
public class PasswordInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PasswordInitializer.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 管理者のパスワードを確実にadmin123に設定
        String adminId = "admin";
        String adminPassword = "admin123";
        
        try {
            var admin = adminMapper.findByAdminId(adminId);
            if (admin != null) {
                // 既存のハッシュと比較（既に正しい場合はスキップ）
                boolean matches = passwordEncoder.matches(adminPassword, admin.getPassword());
                logger.info("既存パスワードハッシュの検証結果: {}", matches);
                
                if (!matches) {
                    logger.info("管理者パスワードを更新します: adminId={}", adminId);
                    String newHash = passwordEncoder.encode(adminPassword);
                    adminMapper.updatePassword(adminId, newHash);
                    logger.info("管理者パスワードを更新しました。新しいハッシュ: {}", newHash);
                } else {
                    logger.info("管理者パスワードは既に正しく設定されています");
                }
            }
        } catch (Exception e) {
            logger.error("パスワード初期化中にエラーが発生しました", e);
        }
    }
}


-- ステップ2: テーブルの作成と初期データ投入
-- clinic_booking_dbデータベースを使用していることを確認してください
-- USE clinic_booking_db; を実行するか、MySQLクライアントでデータベースを選択してください

USE clinic_booking_db;

-- 管理者テーブル
CREATE TABLE IF NOT EXISTS admins (
    admin_id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 患者テーブル
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 営業日テーブル
CREATE TABLE IF NOT EXISTS business_days (
    business_day_id INT AUTO_INCREMENT PRIMARY KEY,
    business_date DATE NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 時間枠テーブル
CREATE TABLE IF NOT EXISTS time_slots (
    time_slot_id INT AUTO_INCREMENT PRIMARY KEY,
    slot_name VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 予約テーブル
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    business_day_id INT NOT NULL,
    time_slot_id INT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_booking (business_day_id, time_slot_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 初期データ投入（管理者）
-- パスワード: admin123 (BCryptハッシュ)
INSERT INTO admins (admin_id, password) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ/6')
ON DUPLICATE KEY UPDATE admin_id=admin_id;

-- 初期データ投入（時間枠）
INSERT INTO time_slots (slot_name, start_time, end_time) VALUES
('午前1', '09:00:00', '09:30:00'),
('午前2', '09:30:00', '10:00:00'),
('午前3', '10:00:00', '10:30:00'),
('午前4', '10:30:00', '11:00:00'),
('午前5', '11:00:00', '11:30:00'),
('午前6', '11:30:00', '12:00:00'),
('午後1', '13:00:00', '13:30:00'),
('午後2', '13:30:00', '14:00:00'),
('午後3', '14:00:00', '14:30:00'),
('午後4', '14:30:00', '15:00:00'),
('午後5', '15:00:00', '15:30:00'),
('午後6', '15:30:00', '16:00:00')
ON DUPLICATE KEY UPDATE slot_name=slot_name;

-- テーブル作成確認
SHOW TABLES;


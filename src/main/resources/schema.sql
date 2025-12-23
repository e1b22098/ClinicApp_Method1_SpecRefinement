-- データベース作成（必要に応じて）
-- CREATE DATABASE IF NOT EXISTS clinic_booking_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- USE clinic_booking_db;

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
INSERT INTO admins (admin_id, password) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ/6') -- password: admin123
ON DUPLICATE KEY UPDATE admin_id=admin_id;

-- 初期データ投入（時間枠）
-- 時間枠名は開始時刻（HH:mm形式）で表記
INSERT INTO time_slots (slot_name, start_time, end_time) VALUES
('09:00', '09:00:00', '09:30:00'),
('09:30', '09:30:00', '10:00:00'),
('10:00', '10:00:00', '10:30:00'),
('10:30', '10:30:00', '11:00:00'),
('11:00', '11:00:00', '11:30:00'),
('11:30', '11:30:00', '12:00:00'),
('13:00', '13:00:00', '13:30:00'),
('13:30', '13:30:00', '14:00:00'),
('14:00', '14:00:00', '14:30:00'),
('14:30', '14:30:00', '15:00:00'),
('15:00', '15:00:00', '15:30:00'),
('15:30', '15:30:00', '16:00:00')
ON DUPLICATE KEY UPDATE slot_name=slot_name;


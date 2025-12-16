-- パスワードハッシュを正しく更新するSQL
USE clinic_booking_db;

-- テストユーザーのパスワードを正しいBCryptハッシュに更新
-- パスワード: admin123
UPDATE users 
SET password = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ/6' 
WHERE card_number = '1234567890';

-- 確認
SELECT user_id, card_number, LEFT(password, 30) as password_preview 
FROM users 
WHERE card_number = '1234567890';


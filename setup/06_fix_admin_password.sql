-- 管理者のパスワードハッシュを正しく更新するSQL
USE clinic_booking_db;

-- 管理者のパスワードを正しいBCryptハッシュに更新
-- パスワード: admin123
UPDATE admins 
SET password = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ/6' 
WHERE admin_id = 'admin';

-- 確認
SELECT admin_id, LEFT(password, 30) as password_preview 
FROM admins 
WHERE admin_id = 'admin';


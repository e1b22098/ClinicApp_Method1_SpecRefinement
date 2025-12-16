-- ユーザー情報の確認用SQL
-- データベースに保存されているユーザー情報を確認します

USE clinic_booking_db;

-- 全ユーザー一覧
SELECT user_id, card_number, name, phone_number, 
       LEFT(password, 20) as password_preview, 
       created_at 
FROM users;

-- 特定の診察券番号で検索（実際の診察券番号に置き換えてください）
-- SELECT * FROM users WHERE card_number = 'your_card_number';


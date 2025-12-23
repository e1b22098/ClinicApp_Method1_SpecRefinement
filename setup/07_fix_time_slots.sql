-- 時間枠の文字化けを修正し、開始時刻で表記するSQL
USE clinic_booking_db;

-- 時間枠データを開始時刻（HH:mm形式）で更新
UPDATE time_slots SET slot_name = '09:00' WHERE time_slot_id = 1;
UPDATE time_slots SET slot_name = '09:30' WHERE time_slot_id = 2;
UPDATE time_slots SET slot_name = '10:00' WHERE time_slot_id = 3;
UPDATE time_slots SET slot_name = '10:30' WHERE time_slot_id = 4;
UPDATE time_slots SET slot_name = '11:00' WHERE time_slot_id = 5;
UPDATE time_slots SET slot_name = '11:30' WHERE time_slot_id = 6;
UPDATE time_slots SET slot_name = '13:00' WHERE time_slot_id = 7;
UPDATE time_slots SET slot_name = '13:30' WHERE time_slot_id = 8;
UPDATE time_slots SET slot_name = '14:00' WHERE time_slot_id = 9;
UPDATE time_slots SET slot_name = '14:30' WHERE time_slot_id = 10;
UPDATE time_slots SET slot_name = '15:00' WHERE time_slot_id = 11;
UPDATE time_slots SET slot_name = '15:30' WHERE time_slot_id = 12;

-- 確認
SELECT time_slot_id, slot_name, start_time, end_time 
FROM time_slots 
ORDER BY start_time;


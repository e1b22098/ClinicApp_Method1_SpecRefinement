package com.clinic.service;

import com.clinic.domain.Booking;
import com.clinic.domain.BusinessDay;
import com.clinic.domain.TimeSlot;
import com.clinic.dto.BookingDetailDto;
import com.clinic.dto.BookingDto;
import com.clinic.repository.BookingMapper;
import com.clinic.repository.BusinessDayMapper;
import com.clinic.repository.TimeSlotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private BusinessDayMapper businessDayMapper;

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    /**
     * 二重予約防止：営業日と時間枠の組み合わせで既存予約をチェック
     */
    public boolean isTimeSlotAvailable(Integer businessDayId, Integer timeSlotId) {
        Booking existingBooking = bookingMapper.findByBusinessDayAndTimeSlot(businessDayId, timeSlotId);
        return existingBooking == null;
    }

    @Transactional
    public void createBooking(Integer userId, BookingDto dto) {
        // 営業日の存在チェック
        BusinessDay businessDay = businessDayMapper.findById(dto.getBusinessDayId());
        if (businessDay == null) {
            throw new IllegalArgumentException("指定された営業日が見つかりません");
        }

        // 営業日が有効かチェック
        if (!businessDay.getIsActive()) {
            throw new IllegalArgumentException("指定された営業日は予約可能ではありません");
        }

        // 過去日付チェック
        if (businessDay.getBusinessDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("過去の日付には予約できません");
        }

        // 時間枠の存在チェック
        TimeSlot timeSlot = timeSlotMapper.findById(dto.getTimeSlotId());
        if (timeSlot == null) {
            throw new IllegalArgumentException("指定された時間枠が見つかりません");
        }

        // 二重予約チェック
        if (!isTimeSlotAvailable(dto.getBusinessDayId(), dto.getTimeSlotId())) {
            throw new IllegalStateException("この時間枠は既に予約されています");
        }

        // 予約作成
        Booking booking = new Booking(userId, dto.getBusinessDayId(), dto.getTimeSlotId());
        bookingMapper.insert(booking);
    }

    public List<Booking> getUserBookings(Integer userId) {
        return bookingMapper.findByUserId(userId);
    }

    @Transactional
    public void cancelBooking(Integer bookingId, Integer userId) {
        Booking booking = bookingMapper.findById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("予約が見つかりません");
        }

        // 本人の予約かチェック
        if (!booking.getUserId().equals(userId)) {
            throw new IllegalStateException("他の患者の予約はキャンセルできません");
        }

        // 既にキャンセル済みかチェック
        if (!"ACTIVE".equals(booking.getStatus())) {
            throw new IllegalStateException("この予約は既にキャンセルされています");
        }

        bookingMapper.cancelBooking(bookingId);
    }

    public List<BookingDetailDto> getBookingDetailsByDate(LocalDate date) {
        return bookingMapper.findBookingDetailsByDate(date);
    }
}


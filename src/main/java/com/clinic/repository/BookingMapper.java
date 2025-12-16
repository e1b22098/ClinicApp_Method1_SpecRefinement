package com.clinic.repository;

import com.clinic.domain.Booking;
import com.clinic.dto.BookingDetailDto;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookingMapper {
    Booking findByBusinessDayAndTimeSlot(Integer businessDayId, Integer timeSlotId);
    List<Booking> findByUserId(Integer userId);
    List<BookingDetailDto> findBookingDetailsByDate(LocalDate date);
    void insert(Booking booking);
    void cancelBooking(Integer bookingId);
    Booking findById(Integer bookingId);
}


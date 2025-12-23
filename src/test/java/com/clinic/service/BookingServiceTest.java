package com.clinic.service;

import com.clinic.domain.Booking;
import com.clinic.domain.BusinessDay;
import com.clinic.domain.TimeSlot;
import com.clinic.dto.BookingDto;
import com.clinic.repository.BookingMapper;
import com.clinic.repository.BusinessDayMapper;
import com.clinic.repository.TimeSlotMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private BusinessDayMapper businessDayMapper;

    @Mock
    private TimeSlotMapper timeSlotMapper;

    @InjectMocks
    private BookingService bookingService;

    private BusinessDay businessDay;
    private TimeSlot timeSlot;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        businessDay = new BusinessDay();
        businessDay.setBusinessDayId(1);
        businessDay.setBusinessDate(LocalDate.now().plusDays(1));
        businessDay.setIsActive(true);

        timeSlot = new TimeSlot();
        timeSlot.setTimeSlotId(1);
        timeSlot.setSlotName("午前1");
        timeSlot.setStartTime(LocalTime.of(9, 0));
        timeSlot.setEndTime(LocalTime.of(9, 30));

        bookingDto = new BookingDto();
        bookingDto.setBusinessDayId(1);
        bookingDto.setTimeSlotId(1);
    }

    @Test
    void testIsTimeSlotAvailable_WhenAvailable() {
        when(bookingMapper.findByBusinessDayAndTimeSlot(anyInt(), anyInt())).thenReturn(null);

        boolean result = bookingService.isTimeSlotAvailable(1, 1);

        assertTrue(result);
    }

    @Test
    void testIsTimeSlotAvailable_WhenNotAvailable() {
        Booking existingBooking = new Booking();
        when(bookingMapper.findByBusinessDayAndTimeSlot(anyInt(), anyInt())).thenReturn(existingBooking);

        boolean result = bookingService.isTimeSlotAvailable(1, 1);

        assertFalse(result);
    }

    @Test
    void testCreateBooking_Success() {
        when(businessDayMapper.findById(1)).thenReturn(businessDay);
        when(timeSlotMapper.findById(1)).thenReturn(timeSlot);
        when(bookingMapper.findByBusinessDayAndTimeSlot(1, 1)).thenReturn(null);
        doAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setBookingId(1);
            return null;
        }).when(bookingMapper).insert(any(Booking.class));

        assertDoesNotThrow(() -> bookingService.createBooking(1, bookingDto));
        verify(bookingMapper, times(1)).insert(any(Booking.class));
    }

    @Test
    void testCreateBooking_WhenBusinessDayNotFound() {
        when(businessDayMapper.findById(1)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(1, bookingDto);
        });
    }

    @Test
    void testCreateBooking_WhenBusinessDayInactive() {
        businessDay.setIsActive(false);
        when(businessDayMapper.findById(1)).thenReturn(businessDay);

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(1, bookingDto);
        });
    }

    @Test
    void testCreateBooking_WhenPastDate() {
        businessDay.setBusinessDate(LocalDate.now().minusDays(1));
        when(businessDayMapper.findById(1)).thenReturn(businessDay);

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(1, bookingDto);
        });
    }

    @Test
    void testCreateBooking_WhenTimeSlotNotFound() {
        when(businessDayMapper.findById(1)).thenReturn(businessDay);
        when(timeSlotMapper.findById(1)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(1, bookingDto);
        });
    }

    @Test
    void testCreateBooking_WhenDuplicateBooking() {
        Booking existingBooking = new Booking();
        when(businessDayMapper.findById(1)).thenReturn(businessDay);
        when(timeSlotMapper.findById(1)).thenReturn(timeSlot);
        when(bookingMapper.findByBusinessDayAndTimeSlot(1, 1)).thenReturn(existingBooking);

        assertThrows(IllegalStateException.class, () -> {
            bookingService.createBooking(1, bookingDto);
        });
    }

    @Test
    void testCancelBooking_Success() {
        Booking booking = new Booking();
        booking.setBookingId(1);
        booking.setUserId(1);
        booking.setStatus("ACTIVE");

        when(bookingMapper.findById(1)).thenReturn(booking);

        assertDoesNotThrow(() -> bookingService.cancelBooking(1, 1));
        verify(bookingMapper, times(1)).cancelBooking(1);
    }

    @Test
    void testCancelBooking_WhenBookingNotFound() {
        when(bookingMapper.findById(1)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.cancelBooking(1, 1);
        });
    }

    @Test
    void testCancelBooking_WhenNotOwner() {
        Booking booking = new Booking();
        booking.setBookingId(1);
        booking.setUserId(2);
        booking.setStatus("ACTIVE");

        when(bookingMapper.findById(1)).thenReturn(booking);

        assertThrows(IllegalStateException.class, () -> {
            bookingService.cancelBooking(1, 1);
        });
    }

    @Test
    void testCancelBooking_WhenAlreadyCancelled() {
        Booking booking = new Booking();
        booking.setBookingId(1);
        booking.setUserId(1);
        booking.setStatus("CANCELLED");

        when(bookingMapper.findById(1)).thenReturn(booking);

        assertThrows(IllegalStateException.class, () -> {
            bookingService.cancelBooking(1, 1);
        });
    }
}


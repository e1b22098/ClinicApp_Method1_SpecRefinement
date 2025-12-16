package com.clinic.domain;

import java.time.LocalDateTime;

public class Booking {
    private Integer bookingId;
    private Integer userId;
    private Integer businessDayId;
    private Integer timeSlotId;
    private LocalDateTime bookingDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 関連エンティティ（表示用）
    private User user;
    private BusinessDay businessDay;
    private TimeSlot timeSlot;

    public Booking() {
    }

    public Booking(Integer userId, Integer businessDayId, Integer timeSlotId) {
        this.userId = userId;
        this.businessDayId = businessDayId;
        this.timeSlotId = timeSlotId;
        this.status = "ACTIVE";
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinessDayId() {
        return businessDayId;
    }

    public void setBusinessDayId(Integer businessDayId) {
        this.businessDayId = businessDayId;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BusinessDay getBusinessDay() {
        return businessDay;
    }

    public void setBusinessDay(BusinessDay businessDay) {
        this.businessDay = businessDay;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}


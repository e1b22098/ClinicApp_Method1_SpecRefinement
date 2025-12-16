package com.clinic.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BusinessDay {
    private Integer businessDayId;
    private LocalDate businessDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BusinessDay() {
    }

    public BusinessDay(LocalDate businessDate, Boolean isActive) {
        this.businessDate = businessDate;
        this.isActive = isActive;
    }

    public Integer getBusinessDayId() {
        return businessDayId;
    }

    public void setBusinessDayId(Integer businessDayId) {
        this.businessDayId = businessDayId;
    }

    public LocalDate getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(LocalDate businessDate) {
        this.businessDate = businessDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
}


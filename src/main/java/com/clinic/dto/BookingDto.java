package com.clinic.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookingDto {
    @NotNull(message = "営業日を選択してください")
    private Integer businessDayId;

    @NotNull(message = "時間枠を選択してください")
    private Integer timeSlotId;

    private LocalDate selectedDate;
    private String businessDateStr;

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

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getBusinessDateStr() {
        return businessDateStr;
    }

    public void setBusinessDateStr(String businessDateStr) {
        this.businessDateStr = businessDateStr;
    }
}


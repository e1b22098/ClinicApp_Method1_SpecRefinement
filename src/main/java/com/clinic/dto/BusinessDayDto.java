package com.clinic.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class BusinessDayDto {
    @NotBlank(message = "日付は必須です")
    private String businessDateStr;

    private Boolean isActive;

    public String getBusinessDateStr() {
        return businessDateStr;
    }

    public void setBusinessDateStr(String businessDateStr) {
        this.businessDateStr = businessDateStr;
    }

    public LocalDate getBusinessDate() {
        if (businessDateStr == null || businessDateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(businessDateStr);
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}


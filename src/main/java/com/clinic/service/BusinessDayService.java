package com.clinic.service;

import com.clinic.domain.BusinessDay;
import com.clinic.dto.BusinessDayDto;
import com.clinic.repository.BusinessDayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BusinessDayService {

    @Autowired
    private BusinessDayMapper businessDayMapper;

    public List<BusinessDay> getActiveBusinessDays() {
        return businessDayMapper.findActiveBusinessDays();
    }

    public List<BusinessDay> getAllBusinessDays() {
        return businessDayMapper.findAll();
    }

    public BusinessDay getBusinessDayByDate(LocalDate date) {
        return businessDayMapper.findByBusinessDate(date);
    }

    @Transactional
    public void registerBusinessDay(BusinessDayDto dto) {
        LocalDate businessDate = dto.getBusinessDate();

        // 過去日付チェック
        if (businessDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("過去の日付は登録できません");
        }

        // 既存チェック
        BusinessDay existing = businessDayMapper.findByBusinessDate(businessDate);
        if (existing != null) {
            throw new IllegalArgumentException("この日付は既に登録されています");
        }

        BusinessDay businessDay = new BusinessDay(businessDate, dto.getIsActive() != null ? dto.getIsActive() : true);
        businessDayMapper.insert(businessDay);
    }

    @Transactional
    public void updateActiveStatus(Integer businessDayId, Boolean isActive) {
        BusinessDay businessDay = businessDayMapper.findById(businessDayId);
        if (businessDay == null) {
            throw new IllegalArgumentException("営業日が見つかりません");
        }

        businessDayMapper.updateActiveStatus(businessDayId, isActive);
    }
}


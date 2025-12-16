package com.clinic.repository;

import com.clinic.domain.BusinessDay;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BusinessDayMapper {
    List<BusinessDay> findActiveBusinessDays();
    BusinessDay findByBusinessDate(LocalDate businessDate);
    BusinessDay findById(Integer businessDayId);
    void insert(BusinessDay businessDay);
    void updateActiveStatus(Integer businessDayId, Boolean isActive);
    List<BusinessDay> findAll();
}


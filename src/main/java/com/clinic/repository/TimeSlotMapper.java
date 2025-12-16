package com.clinic.repository;

import com.clinic.domain.TimeSlot;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TimeSlotMapper {
    List<TimeSlot> findAll();
    TimeSlot findById(Integer timeSlotId);
}


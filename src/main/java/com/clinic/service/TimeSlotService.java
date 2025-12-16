package com.clinic.service;

import com.clinic.domain.TimeSlot;
import com.clinic.repository.TimeSlotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotMapper.findAll();
    }

    public TimeSlot getTimeSlotById(Integer timeSlotId) {
        return timeSlotMapper.findById(timeSlotId);
    }
}


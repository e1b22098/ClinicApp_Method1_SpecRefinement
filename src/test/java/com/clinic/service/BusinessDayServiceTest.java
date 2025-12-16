package com.clinic.service;

import com.clinic.domain.BusinessDay;
import com.clinic.dto.BusinessDayDto;
import com.clinic.repository.BusinessDayMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessDayServiceTest {

    @Mock
    private BusinessDayMapper businessDayMapper;

    @InjectMocks
    private BusinessDayService businessDayService;

    private BusinessDayDto businessDayDto;

    @BeforeEach
    void setUp() {
        businessDayDto = new BusinessDayDto();
        businessDayDto.setBusinessDateStr(LocalDate.now().plusDays(1).toString());
        businessDayDto.setIsActive(true);
    }

    @Test
    void testRegisterBusinessDay_Success() {
        when(businessDayMapper.findByBusinessDate(any(LocalDate.class))).thenReturn(null);
        doAnswer(invocation -> {
            BusinessDay bd = invocation.getArgument(0);
            bd.setBusinessDayId(1);
            return null;
        }).when(businessDayMapper).insert(any(BusinessDay.class));

        assertDoesNotThrow(() -> businessDayService.registerBusinessDay(businessDayDto));
        verify(businessDayMapper, times(1)).insert(any(BusinessDay.class));
    }

    @Test
    void testRegisterBusinessDay_WhenPastDate() {
        businessDayDto.setBusinessDateStr(LocalDate.now().minusDays(1).toString());

        assertThrows(IllegalArgumentException.class, () -> {
            businessDayService.registerBusinessDay(businessDayDto);
        });
    }

    @Test
    void testRegisterBusinessDay_WhenAlreadyExists() {
        BusinessDay existing = new BusinessDay();
        when(businessDayMapper.findByBusinessDate(any(LocalDate.class))).thenReturn(existing);

        assertThrows(IllegalArgumentException.class, () -> {
            businessDayService.registerBusinessDay(businessDayDto);
        });
    }

    @Test
    void testUpdateActiveStatus_Success() {
        BusinessDay businessDay = new BusinessDay();
        businessDay.setBusinessDayId(1);
        when(businessDayMapper.findById(1)).thenReturn(businessDay);

        assertDoesNotThrow(() -> businessDayService.updateActiveStatus(1, false));
        verify(businessDayMapper, times(1)).updateActiveStatus(1, false);
    }

    @Test
    void testUpdateActiveStatus_WhenNotFound() {
        when(businessDayMapper.findById(1)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            businessDayService.updateActiveStatus(1, false);
        });
    }
}


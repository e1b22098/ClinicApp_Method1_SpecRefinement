package com.clinic.controller;

import com.clinic.domain.BusinessDay;
import com.clinic.dto.BookingDetailDto;
import com.clinic.dto.BusinessDayDto;
import com.clinic.service.BookingService;
import com.clinic.service.BusinessDayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusinessDayService businessDayService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/bookings")
    public String bookingList(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             Model model) {
        if (date == null) {
            date = LocalDate.now();
        }

        List<BookingDetailDto> bookings = bookingService.getBookingDetailsByDate(date);
        model.addAttribute("bookings", bookings);
        model.addAttribute("selectedDate", date);
        return "admin/booking-list";
    }

    @GetMapping("/business-days")
    public String businessDayList(Model model) {
        List<BusinessDay> businessDays = businessDayService.getAllBusinessDays();
        model.addAttribute("businessDays", businessDays);
        model.addAttribute("businessDayDto", new BusinessDayDto());
        return "admin/business-day-list";
    }

    @PostMapping("/business-days/register")
    public String registerBusinessDay(@Valid @ModelAttribute BusinessDayDto dto, BindingResult result,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "入力内容に誤りがあります。");
            return "redirect:/admin/business-days";
        }

        try {
            businessDayService.registerBusinessDay(dto);
            redirectAttributes.addFlashAttribute("success", "営業日を登録しました。");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/business-days";
    }

    @PostMapping("/business-days/{businessDayId}/toggle")
    public String toggleBusinessDay(@PathVariable Integer businessDayId,
                                   @RequestParam Boolean isActive,
                                   RedirectAttributes redirectAttributes) {
        try {
            businessDayService.updateActiveStatus(businessDayId, isActive);
            redirectAttributes.addFlashAttribute("success", "営業日の状態を更新しました。");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/business-days";
    }
}


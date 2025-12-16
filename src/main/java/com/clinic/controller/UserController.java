package com.clinic.controller;

import com.clinic.domain.Booking;
import com.clinic.domain.BusinessDay;
import com.clinic.domain.TimeSlot;
import com.clinic.domain.User;
import com.clinic.dto.BookingDto;
import com.clinic.dto.PasswordResetDto;
import com.clinic.dto.UserRegistrationDto;
import com.clinic.service.BookingService;
import com.clinic.service.BusinessDayService;
import com.clinic.service.TimeSlotService;
import com.clinic.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusinessDayService businessDayService;

    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegistrationDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/register";
        }

        try {
            userService.register(dto);
            model.addAttribute("success", "登録が完了しました。ログインしてください。");
            return "user/register";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "user/register";
        }
    }

    @GetMapping("/password-reset")
    public String showPasswordResetForm(Model model) {
        model.addAttribute("passwordResetDto", new PasswordResetDto());
        return "user/password-reset";
    }

    @PostMapping("/password-reset")
    public String resetPassword(@Valid @ModelAttribute PasswordResetDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/password-reset";
        }

        try {
            userService.resetPassword(dto);
            model.addAttribute("success", "パスワードをリセットしました。新しいパスワードでログインしてください。");
            return "user/password-reset";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "user/password-reset";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cardNumber = auth.getName();
        User user = userService.findByCardNumber(cardNumber);
        model.addAttribute("user", user);
        return "user/dashboard";
    }

    @GetMapping("/booking/create")
    public String showBookingForm(Model model) {
        List<BusinessDay> businessDays = businessDayService.getActiveBusinessDays();
        List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlots();

        model.addAttribute("bookingDto", new BookingDto());
        model.addAttribute("businessDays", businessDays);
        model.addAttribute("timeSlots", timeSlots);
        return "user/booking-create";
    }

    @PostMapping("/booking/create")
    public String createBooking(@Valid @ModelAttribute BookingDto dto, BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "入力内容に誤りがあります。");
            return "redirect:/user/booking/create";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cardNumber = auth.getName();
        User user = userService.findByCardNumber(cardNumber);

        try {
            bookingService.createBooking(user.getUserId(), dto);
            redirectAttributes.addFlashAttribute("success", "予約が完了しました。");
            return "redirect:/user/booking/confirm";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/booking/create";
        }
    }

    @GetMapping("/booking/confirm")
    public String confirmBooking(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cardNumber = auth.getName();
        User user = userService.findByCardNumber(cardNumber);
        List<Booking> bookings = bookingService.getUserBookings(user.getUserId());
        model.addAttribute("bookings", bookings);
        return "user/booking-confirm";
    }

    @GetMapping("/booking/list")
    public String bookingList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cardNumber = auth.getName();
        User user = userService.findByCardNumber(cardNumber);
        List<Booking> bookings = bookingService.getUserBookings(user.getUserId());
        model.addAttribute("bookings", bookings);
        return "user/booking-list";
    }

    @PostMapping("/booking/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Integer bookingId, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cardNumber = auth.getName();
        User user = userService.findByCardNumber(cardNumber);

        try {
            bookingService.cancelBooking(bookingId, user.getUserId());
            redirectAttributes.addFlashAttribute("success", "予約をキャンセルしました。");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/user/booking/list";
    }
}


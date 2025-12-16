package com.clinic.service;

import com.clinic.domain.User;
import com.clinic.dto.PasswordResetDto;
import com.clinic.dto.UserRegistrationDto;
import com.clinic.repository.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRegistrationDto registrationDto;
    private PasswordResetDto passwordResetDto;

    @BeforeEach
    void setUp() {
        registrationDto = new UserRegistrationDto();
        registrationDto.setName("テスト太郎");
        registrationDto.setBirthDateStr("1990-01-01");
        registrationDto.setPhoneNumber("09012345678");
        registrationDto.setCardNumber("1234567890");
        registrationDto.setPassword("password123");
        registrationDto.setPasswordConfirm("password123");

        passwordResetDto = new PasswordResetDto();
        passwordResetDto.setCardNumber("1234567890");
        passwordResetDto.setPhoneNumber("09012345678");
        passwordResetDto.setNewPassword("newpassword123");
        passwordResetDto.setPasswordConfirm("newpassword123");
    }

    @Test
    void testRegister_Success() {
        when(userMapper.findByCardNumber(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userService.register(registrationDto));
        verify(userMapper, times(1)).insert(any(User.class));
    }

    @Test
    void testRegister_WhenCardNumberExists() {
        User existingUser = new User();
        when(userMapper.findByCardNumber(anyString())).thenReturn(existingUser);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.register(registrationDto);
        });
    }

    @Test
    void testRegister_WhenPasswordMismatch() {
        registrationDto.setPasswordConfirm("differentPassword");
        when(userMapper.findByCardNumber(anyString())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.register(registrationDto);
        });
    }

    @Test
    void testRegister_WhenFutureBirthDate() {
        registrationDto.setBirthDateStr(LocalDate.now().plusDays(1).toString());
        when(userMapper.findByCardNumber(anyString())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.register(registrationDto);
        });
    }

    @Test
    void testResetPassword_Success() {
        User user = new User();
        user.setCardNumber("1234567890");
        when(userMapper.findByCardNumberAndPhoneNumber(anyString(), anyString())).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userService.resetPassword(passwordResetDto));
        verify(userMapper, times(1)).updatePassword(anyString(), anyString());
    }

    @Test
    void testResetPassword_WhenUserNotFound() {
        when(userMapper.findByCardNumberAndPhoneNumber(anyString(), anyString())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.resetPassword(passwordResetDto);
        });
    }

    @Test
    void testResetPassword_WhenPasswordMismatch() {
        User user = new User();
        passwordResetDto.setPasswordConfirm("differentPassword");
        when(userMapper.findByCardNumberAndPhoneNumber(anyString(), anyString())).thenReturn(user);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.resetPassword(passwordResetDto);
        });
    }
}


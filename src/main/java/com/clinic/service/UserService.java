package com.clinic.service;

import com.clinic.domain.User;
import com.clinic.dto.PasswordResetDto;
import com.clinic.dto.UserRegistrationDto;
import com.clinic.repository.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByCardNumber(String cardNumber) {
        return userMapper.findByCardNumber(cardNumber);
    }

    @Transactional
    public void register(UserRegistrationDto dto) {
        // 診察券番号の重複チェック
        User existingUser = userMapper.findByCardNumber(dto.getCardNumber());
        if (existingUser != null) {
            throw new IllegalArgumentException("この診察券番号は既に登録されています");
        }

        // パスワード確認チェック
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new IllegalArgumentException("パスワードとパスワード確認が一致しません");
        }

        // 過去日付チェック
        if (dto.getBirthDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("生年月日に未来の日付は指定できません");
        }

        User user = new User();
        user.setCardNumber(dto.getCardNumber());
        user.setName(dto.getName());
        user.setBirthDate(dto.getBirthDate());
        user.setPhoneNumber(dto.getPhoneNumber());
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);

        logger.debug("Registering user: cardNumber={}, name={}, passwordHash={}", 
            dto.getCardNumber(), dto.getName(), encodedPassword);
        
        userMapper.insert(user);
        
        logger.info("User registered successfully: cardNumber={}, userId={}", 
            dto.getCardNumber(), user.getUserId());
    }

    @Transactional
    public void resetPassword(PasswordResetDto dto) {
        // 診察券番号と電話番号でユーザーを検索
        User user = userMapper.findByCardNumberAndPhoneNumber(dto.getCardNumber(), dto.getPhoneNumber());
        if (user == null) {
            throw new IllegalArgumentException("診察券番号と電話番号が一致するユーザーが見つかりません");
        }

        // パスワード確認チェック
        if (!dto.getNewPassword().equals(dto.getPasswordConfirm())) {
            throw new IllegalArgumentException("パスワードとパスワード確認が一致しません");
        }

        userMapper.updatePassword(dto.getCardNumber(), passwordEncoder.encode(dto.getNewPassword()));
    }
}


package com.clinic.repository;

import com.clinic.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByCardNumber(String cardNumber);
    User findByCardNumberAndPhoneNumber(String cardNumber, String phoneNumber);
    void insert(User user);
    void updatePassword(String cardNumber, String password);
}


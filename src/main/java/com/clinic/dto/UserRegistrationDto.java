package com.clinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class UserRegistrationDto {
    @NotBlank(message = "氏名は必須です")
    private String name;

    @NotBlank(message = "生年月日は必須です")
    private String birthDateStr;

    @NotBlank(message = "電話番号は必須です")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "電話番号は10桁または11桁の数値で入力してください")
    private String phoneNumber;

    @NotBlank(message = "診察券番号は必須です")
    @Pattern(regexp = "^[0-9]+$", message = "診察券番号は数値で入力してください")
    private String cardNumber;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 6, message = "パスワードは6文字以上で入力してください")
    private String password;

    @NotBlank(message = "パスワード確認は必須です")
    private String passwordConfirm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDateStr() {
        return birthDateStr;
    }

    public void setBirthDateStr(String birthDateStr) {
        this.birthDateStr = birthDateStr;
    }

    public LocalDate getBirthDate() {
        if (birthDateStr == null || birthDateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(birthDateStr);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}


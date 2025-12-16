package com.clinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordResetDto {
    @NotBlank(message = "診察券番号は必須です")
    @Pattern(regexp = "^[0-9]+$", message = "診察券番号は数値で入力してください")
    private String cardNumber;

    @NotBlank(message = "電話番号は必須です")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "電話番号は10桁または11桁の数値で入力してください")
    private String phoneNumber;

    @NotBlank(message = "新しいパスワードは必須です")
    @Size(min = 6, message = "パスワードは6文字以上で入力してください")
    private String newPassword;

    @NotBlank(message = "パスワード確認は必須です")
    private String passwordConfirm;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}


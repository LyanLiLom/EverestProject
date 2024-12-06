package com.example.everest.service;

public interface SendOTPtoEmailService {
    String generateOTP();
    void sendEmail(String email);
    int checkOTP(String otp);

}

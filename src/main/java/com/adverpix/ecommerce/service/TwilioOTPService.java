package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.config.TwilioConfig;
import com.adverpix.ecommerce.dto.OtpStatus;
import com.adverpix.ecommerce.dto.PasswordResetRequestDto;
import com.adverpix.ecommerce.dto.PasswordResetResponseDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioOTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    private final Map<String, String> otpMap = new HashMap<>();

    /**
     * Generates a 6-digit OTP and sends via Twilio
     */
    public PasswordResetResponseDto sendOTPForPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {
        try {
            PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String otp = generateOTP();

            String otpMessage = "Dear Customer, Your OTP is ##" + otp + "##. Use this Passcode to complete your transaction. Thank You.";
            Message.creator(to, from, otpMessage).create();

            otpMap.put(passwordResetRequestDto.getUserName(), otp);
            return new PasswordResetResponseDto(OtpStatus.DELIVERED, otpMessage);

        } catch (Exception ex) {
            return new PasswordResetResponseDto(OtpStatus.FAILED, ex.getMessage());
        }
    }

    /**
     * Validates the OTP entered by the user
     */
    public String validateOTP(String userInputOtp, String userName) {
        if (userInputOtp.equals(otpMap.get(userName))) {
            otpMap.remove(userName);
            return "Valid OTP! You may proceed.";
        } else {
            throw new IllegalArgumentException("Invalid OTP, please retry!");
        }
    }

    /**
     * Generates a 6-digit OTP
     */
    private String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}

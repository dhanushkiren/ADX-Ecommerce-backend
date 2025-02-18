package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.dto.PasswordResetRequestDto;
import com.adverpix.ecommerce.dto.PasswordResetResponseDto;
import com.adverpix.ecommerce.service.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class TwilioOTPController {

    @Autowired
    private TwilioOTPService otpService;

    /**
     * Endpoint to send OTP for password reset
     */
    @PostMapping("/send")
    public ResponseEntity<PasswordResetResponseDto> sendOTP(@RequestBody PasswordResetRequestDto requestDto) {
        PasswordResetResponseDto response = otpService.sendOTPForPasswordReset(requestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to validate OTP
     */
    @PostMapping("/validate")
    public ResponseEntity<String> validateOTP(@RequestParam String otp, @RequestParam String userName) {
        String result = otpService.validateOTP(otp, userName);
        return ResponseEntity.ok(result);
    }
}

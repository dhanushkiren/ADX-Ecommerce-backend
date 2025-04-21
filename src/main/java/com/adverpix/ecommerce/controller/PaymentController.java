package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.dto.ProductRequest;
import com.adverpix.ecommerce.dto.RazorpayResponse;
import com.adverpix.ecommerce.service.RazorPayService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/razorpay")
public class PaymentController {
    @Autowired
    private RazorPayService razorPayService;

    @PostMapping("/checkout")
    public RazorpayResponse checkout(@RequestBody ProductRequest request) {
        try {
            return razorPayService.createPaymentLink(request);
        } catch (RazorpayException e) {
            return RazorpayResponse.builder()
                    .status("FAILED")
                    .message("Error creating Razorpay payment link: " + e.getMessage())
                    .paymentUrl(null)
                    .build();
        }
    }
}




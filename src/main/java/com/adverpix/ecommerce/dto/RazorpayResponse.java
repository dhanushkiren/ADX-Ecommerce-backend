package com.adverpix.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RazorpayResponse {
    private String status;
    private String message;
    private String paymentUrl;
}

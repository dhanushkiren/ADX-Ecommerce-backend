package com.adverpix.ecommerce.service;


import com.adverpix.ecommerce.dto.ProductRequest;
import com.adverpix.ecommerce.dto.RazorpayResponse;
import com.razorpay.Order;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorPayService {

    @Value("${razorpay.keyId}")
    private String apiKey;

    @Value("${razorpay.secretKey}")
    private String apiSecret;

    public RazorpayResponse createPaymentLink(ProductRequest request) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(apiKey, apiSecret);

        JSONObject customer = new JSONObject();
        customer.put("name", request.getName());
        customer.put("email", "test@example.com");
        customer.put("contact", "9123456789");

        JSONObject payload = new JSONObject();
        payload.put("amount", request.getAmount());
        payload.put("currency", request.getCurrency());
        payload.put("accept_partial", false);
        payload.put("description", "Payment for " + request.getName());
        payload.put("customer", customer);
        payload.put("notify", new JSONObject().put("sms", true).put("email", true));
        payload.put("callback_url", "http://localhost:8080/payment-success");
        payload.put("callback_method", "get");

        PaymentLink link = client.paymentLink.create(payload);

        return RazorpayResponse.builder()
                .status("SUCCESS")
                .message("Payment link created")
                .paymentUrl(link.get("short_url"))
                .build();
    }
}


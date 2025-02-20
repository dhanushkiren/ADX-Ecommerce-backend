package com.adverpix.ecommerce.controller;


import com.adverpix.ecommerce.dto.ProductRequest;
import com.adverpix.ecommerce.dto.StripeResponse;
import com.adverpix.ecommerce.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class ProductCheckoutController {


    private StripeService stripeService;

    public ProductCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }
}

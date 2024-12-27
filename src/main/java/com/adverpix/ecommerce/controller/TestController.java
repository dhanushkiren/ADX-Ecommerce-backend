package com.adverpix.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test Endpoint", description = "sample endpoint to test the swagger")
@RequestMapping("api/test")
public class TestController {


    @GetMapping
    @Operation(summary = "Test API", description = "Returns a greeting message")
    public String Test(){

        return "Hello World";
    }
}

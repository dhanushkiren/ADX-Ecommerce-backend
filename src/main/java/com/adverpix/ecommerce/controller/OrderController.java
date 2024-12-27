package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.dto.OrderRequestDto;
import com.adverpix.ecommerce.dto.OrderResponseDto;
import com.adverpix.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequest) {
        OrderResponseDto createdOrder = orderService.createOrder(orderRequest);
        // Check if the created order is null or empty (though ideally it shouldn't be null)
        if (createdOrder == null || createdOrder.getOrderItems().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Or any appropriate status code
        }
        return ResponseEntity.status(201).body(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        if(order == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Order found for the ID" + id);
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUserId(userId);
        if(orders.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for user with ID: " + userId);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
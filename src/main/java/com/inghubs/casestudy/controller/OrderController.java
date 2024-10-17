package com.inghubs.casestudy.controller;

import com.inghubs.casestudy.model.Order;
import com.inghubs.casestudy.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/list")
    public List<Order> listOrders() {
        return orderService.listOrders();
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}


package com.inghubs.casestudy.controller;

import com.inghubs.casestudy.service.OrderMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderMatchingService orderMatchingService;

    @PostMapping("/match")
    public String matchPendingOrders() {
        return orderMatchingService.matchPendingOrders();
    }
}


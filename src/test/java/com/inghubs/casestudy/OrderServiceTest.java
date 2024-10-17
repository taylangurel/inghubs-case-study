package com.inghubs.casestudy;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.model.Order;
import com.inghubs.casestudy.model.OrderSide;
import com.inghubs.casestudy.model.OrderStatus;
import com.inghubs.casestudy.repository.AssetRepository;
import com.inghubs.casestudy.repository.OrderRepository;
import com.inghubs.casestudy.service.CustomUserDetailsService;
import com.inghubs.casestudy.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_shouldCreateOrder_whenCustomerHasEnoughFunds() {
        Asset tryAsset = new Asset(1L, 1L, "TRY", 10000.0, 10000.0);
        Order order = new Order(null, 1L, "AAPL", OrderSide.BUY, 10.0, 100.0, null, new Date());

        when(assetRepository.findByCustomerId(1L)).thenReturn(Collections.singletonList(tryAsset));
        when(orderRepository.save(order)).thenReturn(order);
        when(customUserDetailsService.getCurrentUserId()).thenReturn(1L);

        Order createdOrder = orderService.createOrder(order);

        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
        verify(assetRepository, times(1)).save(tryAsset);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void deleteOrder_shouldDeleteOrder_whenOrderIsPending() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order(1L, 1L, "AAPL", OrderSide.BUY, 10.0, 100.0, OrderStatus.PENDING, new Date());
        orders.add(order);
        Asset tryAsset = new Asset(1L, 1L, "TRY", 10000.0, 9000.0);

        when(orderRepository.findByCustomerId(1L)).thenReturn(orders);
        when(assetRepository.findByCustomerId(1L)).thenReturn(Collections.singletonList(tryAsset));
        when(customUserDetailsService.getCurrentUserId()).thenReturn(1L);

        String result = orderService.deleteOrder(1L);

        assertEquals("Order successfully deleted", result);
        verify(assetRepository, times(1)).save(tryAsset);
        verify(orderRepository, times(1)).delete(order);
    }
}


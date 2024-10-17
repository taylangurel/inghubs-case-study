package com.inghubs.casestudy;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.model.Order;
import com.inghubs.casestudy.model.OrderSide;
import com.inghubs.casestudy.model.OrderStatus;
import com.inghubs.casestudy.repository.AssetRepository;
import com.inghubs.casestudy.repository.OrderRepository;
import com.inghubs.casestudy.service.OrderMatchingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class OrderMatchingServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private OrderMatchingService orderMatchingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void matchPendingOrders_shouldMatchOrders_whenConditionsAreMet() {
        Order pendingOrder = new Order(1L, 1L, "AAPL", OrderSide.BUY, 10.0, 100.0, OrderStatus.PENDING, null);
        Asset tryAsset = new Asset(1L, 1L, "TRY", 2000.0, 2000.0);

        when(orderRepository.findByStatus(OrderStatus.PENDING)).thenReturn(Collections.singletonList(pendingOrder));
        when(assetRepository.findByCustomerId(1L)).thenReturn(Collections.singletonList(tryAsset));

        orderMatchingService.matchPendingOrders();

        verify(orderRepository, times(1)).save(pendingOrder);
        verify(assetRepository, times(1)).save(tryAsset);
    }
}


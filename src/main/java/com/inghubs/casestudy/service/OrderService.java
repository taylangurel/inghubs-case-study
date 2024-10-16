package com.inghubs.casestudy.service;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.model.Order;
import com.inghubs.casestudy.model.OrderSide;
import com.inghubs.casestudy.model.OrderStatus;
import com.inghubs.casestudy.repository.AssetRepository;
import com.inghubs.casestudy.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AssetRepository assetRepository;

    public Order createOrder(Order order) {
        // Check if the customer has enough TRY or asset to create the order
        Asset asset = assetRepository.findByCustomerId(order.getCustomerId())
                .stream()
                .filter(a -> a.getAssetName().equals(order.getAssetName()) || a.getAssetName().equals("TRY")) // ! TRY only for now but handling other assets is available
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (order.getOrderSide().name().equals("BUY") && asset.getUsableSize() < order.getSize() * order.getPrice()) {
            throw new RuntimeException("Not enough TRY to create order");
        } else if (order.getOrderSide().name().equals("SELL") && asset.getUsableSize() < order.getSize()) {
            throw new RuntimeException("Not enough assets to sell");
        }

        // Update the usable size accordingly
        if (order.getOrderSide().name().equals("BUY")) {
            asset.setUsableSize(asset.getUsableSize() - order.getSize() * order.getPrice());
        } else {
            asset.setUsableSize(asset.getUsableSize() - order.getSize());
        }

        assetRepository.save(asset);

        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(new Date());

        return orderRepository.save(order);
    }

    public List<Order> listOrders(Long customerId, String dateRange) {
        // For now, listing orders based on customerId
        return orderRepository.findByCustomerId(customerId);
    }

    public String deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be deleted");
        }

        // Update the asset's usable size since the order is being canceled
        Asset asset = assetRepository.findByCustomerId(order.getCustomerId())
                .stream()
                .filter(a -> a.getAssetName().equals(order.getAssetName()) || a.getAssetName().equals("TRY")) // ! TRY only for now but handling other assets is available
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (order.getOrderSide() == OrderSide.BUY) {
            asset.setUsableSize(asset.getUsableSize() + order.getSize() * order.getPrice());
        } else {
            asset.setUsableSize(asset.getUsableSize() + order.getSize());
        }

        assetRepository.save(asset);
        orderRepository.delete(order);
        return "Order successfully deleted";
    }
}


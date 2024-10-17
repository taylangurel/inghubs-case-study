package com.inghubs.casestudy.service;

import com.inghubs.casestudy.model.Asset;
import com.inghubs.casestudy.model.Order;
import com.inghubs.casestudy.model.OrderStatus;
import com.inghubs.casestudy.repository.AssetRepository;
import com.inghubs.casestudy.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMatchingService {

    private final OrderRepository orderRepository;
    private final AssetRepository assetRepository;

    public String matchPendingOrders() {
        List<Order> pendingOrders = orderRepository.findByStatus(OrderStatus.PENDING);

        if (pendingOrders.isEmpty()) {
            return "No pending orders found";
        }

        for (Order order : pendingOrders) {
            Asset tryAsset = assetRepository.findByCustomerId(order.getCustomerId())
                    .stream()
                    .filter(a -> a.getAssetName().equals("TRY"))
                    .findFirst()
                    .orElse(null);

            Asset stockAsset = assetRepository.findByCustomerId(order.getCustomerId())
                    .stream()
                    .filter(a -> a.getAssetName().equals(order.getAssetName()))
                    .findFirst()
                    .orElse(null);

            if (order.getOrderSide().name().equals("BUY") && tryAsset != null) {
                double totalCost = order.getSize() * order.getPrice();
                if (tryAsset.getUsableSize() >= totalCost) {
                    // Update TRY asset and stock asset
                    tryAsset.setUsableSize(tryAsset.getUsableSize() - totalCost);

                    if (stockAsset == null) {
                        // Create new asset entry if it doesn't exist
                        stockAsset = new Asset(null, order.getCustomerId(), order.getAssetName(), order.getSize(), order.getSize());
                    } else {
                        stockAsset.setSize(stockAsset.getSize() + order.getSize());
                        stockAsset.setUsableSize(stockAsset.getUsableSize() + order.getSize());
                    }

                    // Update order status
                    order.setStatus(OrderStatus.MATCHED);

                    assetRepository.save(tryAsset);
                    assetRepository.save(stockAsset);
                    orderRepository.save(order);
                }
            } else if (order.getOrderSide().name().equals("SELL") && stockAsset != null) {
                if (stockAsset.getUsableSize() >= order.getSize()) {
                    // Update stock asset and TRY asset
                    stockAsset.setSize(stockAsset.getSize() - order.getSize());
                    stockAsset.setUsableSize(stockAsset.getUsableSize() - order.getSize());

                    if (tryAsset == null) {
                        // Create a TRY asset if it doesn't exist (shouldn't usually happen)
                        tryAsset = new Asset(null, order.getCustomerId(), "TRY", order.getSize() * order.getPrice(), order.getSize() * order.getPrice());
                    } else {
                        tryAsset.setSize(tryAsset.getSize() + order.getSize() * order.getPrice());
                        tryAsset.setUsableSize(tryAsset.getUsableSize() + order.getSize() * order.getPrice());
                    }

                    // Update order status
                    order.setStatus(OrderStatus.MATCHED);

                    assetRepository.save(stockAsset);
                    assetRepository.save(tryAsset);
                    orderRepository.save(order);
                }
            }
        }

        return "Pending orders have been matched successfully.";
    }
}


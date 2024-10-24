package com.inghubs.casestudy.service;

import com.inghubs.casestudy.exception.NotFoundException;
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
    private final CustomUserDetailsService customUserDetailsService;

    public Order createOrder(Order order) {

        //Load the customer
        Long customerId = customUserDetailsService.getCurrentUserId();
        order.setCustomerId(customerId);

        switch (order.getOrderSide()) {

            case BUY:
                //with TRY only
                Asset tryAsset = getAssetOrThrow(
                        customerId,
                        Constants.TRY_ASSET_NAME,
                        order.getSize() * order.getPrice(),
                        "Not enough TRY to create order");

                tryAsset.setUsableSize(tryAsset.getUsableSize() - order.getSize() * order.getPrice());
                assetRepository.save(tryAsset);
                return prepareAndSaveOrder(order);

            case SELL:
                //Check if the desired asset amount exists
                Asset asset = getAssetOrThrow(
                        customerId,
                        order.getAssetName(),
                        order.getSize(),
                        "Not enough " + order.getAssetName() + " to create order");

                asset.setUsableSize(asset.getUsableSize() - order.getSize());
                assetRepository.save(asset);
                return prepareAndSaveOrder(order);

            default:
                throw new IllegalArgumentException("Order Side not supported: " + order.getOrderSide());
        }
    }

    private Asset getAssetOrThrow(Long customerId, String assetName, double requiredSize, String errorMessage) {
        return assetRepository.findByCustomerId(customerId)
                .stream()
                .filter(a -> a.getAssetName().equals(assetName))
                .filter(a -> a.getSize() >= requiredSize)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(errorMessage));
    }

    private Order prepareAndSaveOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(new Date());
        return orderRepository.save(order);
    }

    public List<Order> listOrders() {

        //Load the customer
        Long customerId = customUserDetailsService.getCurrentUserId();

        // For now, listing orders based on customerId
        return orderRepository.findByCustomerId(customerId);
    }

    public String deleteOrder(Long orderId) {

        //Load the customer
        Long customerId = customUserDetailsService.getCurrentUserId();

        //Load and filter the orders of the customer to match the current order
        Order order = orderRepository.findByCustomerId(customerId)
                .stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Order not found for current customer"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be deleted");
        }

        // Update the asset's usable size since the order is being canceled
        Asset asset = assetRepository.findByCustomerId(order.getCustomerId())
                .stream()
                .filter(a -> a.getAssetName().equals(order.getAssetName()) || a.getAssetName().equals(Constants.TRY_ASSET_NAME))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Asset not found"));

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


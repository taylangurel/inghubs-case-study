package com.inghubs.casestudy.repository;

import com.inghubs.casestudy.model.Order;
import com.inghubs.casestudy.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    List<Order> findByStatus(OrderStatus orderStatus);
}


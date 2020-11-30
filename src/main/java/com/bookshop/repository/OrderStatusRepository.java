package com.bookshop.repository;

import com.bookshop.entity.order.Order;
import com.bookshop.entity.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByOrder(Order order);
}

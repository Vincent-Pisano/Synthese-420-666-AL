package com.synthese.order.service;

import com.synthese.order.model.Item;
import com.synthese.order.model.Order;
import com.synthese.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final Logger logger;
    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.logger = LoggerFactory.getLogger(OrderService.class);
        this.orderRepository = orderRepository;
    }

    public Optional<Order> saveOrder(Order order) {
        Optional<Order> optionalOrder = Optional.empty();
        if (order.getClient() != null) {
            optionalOrder = Optional.of(orderRepository.save(order));
        }
        return optionalOrder;
    }

    public Optional<Order> getWaitingOrder(String idClient) {
        Optional<Order> optionalOrder = orderRepository.findByIdClientAndStatusWaitingAndIsDisabledFalse(idClient);
        optionalOrder.ifPresent(order -> order.getOrderItems().forEach(orderItem -> {
            Item item = orderItem.getItem();
            item.setImage(null);
        }));
        return optionalOrder;
    }
}

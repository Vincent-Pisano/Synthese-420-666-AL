package com.synthese.shipping.service;

import com.synthese.shipping.model.Item;
import com.synthese.shipping.model.Order;
import com.synthese.shipping.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {

    private final Logger logger;
    private final OrderRepository orderRepository;


    ShippingService(OrderRepository orderRepository) {
        this.logger = LoggerFactory.getLogger(ShippingService.class);
        this.orderRepository = orderRepository;
    }

    public Optional<Order> handleOrder(String orderID) {
        Optional<Order> optionalOrder = orderRepository.findById(orderID);
        optionalOrder.ifPresent(this::createShipment);
        return optionalOrder;
    }

    /*
    * On simule une communication avec une microservice de livraison
     */
    private void createShipment(Order order) {
        order.setStatus(Order.OrderStatus.SENT);
        order.setShippingDate(getShippingDate());
        order.getOrderItems().forEach(orderItem -> {
            Item item = orderItem.getItem();
            item.setImage(null);
        });
    }

    private Date getShippingDate() {
        return LocalTime.now().isAfter(LocalTime.parse("10:00")) ? getDatePlusDays(1) : new Date();
    }

    private Date getDatePlusDays(int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    @EventListener(ContextRefreshedEvent.class)
    public void checkForConfirmedOrders() {
        List<Order> orders = orderRepository.findAllByStatusIsConfirmedAndIsDisabledFalse();
        orders.forEach(this::createShipment);
        orderRepository.saveAll(orders);
    }
}

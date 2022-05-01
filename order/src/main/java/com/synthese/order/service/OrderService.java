package com.synthese.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthese.order.model.Item;
import com.synthese.order.model.Order;
import com.synthese.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.synthese.order.utils.Utils.InventoryControllerUrl.*;
import static com.synthese.order.utils.Utils.ShippingControllerUrl.*;

@Service
public class OrderService {

    private final Logger logger;
    private final OrderRepository orderRepository;
    private RestTemplate restTemplate;

    OrderService(OrderRepository orderRepository, RestTemplateBuilder restTemplateBuilder) {
        this.logger = LoggerFactory.getLogger(OrderService.class);
        this.orderRepository = orderRepository;
        this.restTemplate = new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public Optional<Order> saveOrder(Order order) {
        Optional<Order> optionalOrder = Optional.empty();
        if (order.getClient() != null) {
            optionalOrder = Optional.of(orderRepository.save(order));
        }
        return optionalOrder;
    }

    public Optional<List<Item>> confirmOrder(Order order) {
        Optional<List<Item>> optionalItems = Optional.empty();

        if (orderRepository.findById(order.getId()).isPresent()) {
            optionalItems = getItemsWithoutEnoughStock(order);
            if (optionalItems.isEmpty()) 
                createShipment(order);
        }
        return optionalItems;
    }

    private Optional<List<Item>> getItemsWithoutEnoughStock(Order order) {
        Optional<List<Item>> optionalItems = Optional.of(new ArrayList<>());

        try {
            optionalItems = Optional.ofNullable(updateInventory(order));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("An error occurred during the update of the inventory at confirmOrder.getItems in OrderService.java : "
                + e.getMessage());
        }

        return optionalItems;
    }

    private List<Item> updateInventory(Order order) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(new ObjectMapper().writeValueAsString(order.getOrderItems()), headers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return restTemplate.postForObject(URL_UPDATE_ITEM_QUANTITY, request, List.class);
    }

    private void createShipment(Order order) {
        order.setStatus(Order.OrderStatus.CONFIRMED);
        try {
            order = restTemplate.getForObject(URL_HANDLE_ORDER + order.getId(), Order.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("An error occurred during the creation of the shipment at the confirmOrder.createShipment in OrderService.java : "
                    + e.getMessage());
        }
        finally {
            orderRepository.save(order);
        }
    }

    public Optional<Order> getWaitingOrder(String idClient) {
        Optional<Order> optionalOrder = orderRepository.findByIdClientAndStatusWaitingAndIsDisabledFalse(idClient);
        optionalOrder.ifPresent(order -> order.getOrderItems().forEach(orderItem -> {
            Item item = orderItem.getItem();
            item.setImage(null);
        }));
        return optionalOrder;
    }

    public Optional<List<Order>> getAllOrdersOfClient(String idClient) {
        List<Order> orders = orderRepository.findAllByClientIdAndIsDisabledFalse(idClient);
        orders.forEach(order -> order.getOrderItems().forEach(orderItem -> {
            Item item = orderItem.getItem();
            item.setImage(null);
        }));
        return orders.isEmpty() ? Optional.empty() : Optional.of(orders);
    }
}

package com.synthese.order.controller;

import com.synthese.order.model.Order;
import com.synthese.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.synthese.order.utils.Utils.CROSS_ORIGIN_ALLOWED;
import static com.synthese.order.utils.Utils.OrderControllerUrl.*;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping(URL_SAVE_ORDER)
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        return service.saveOrder(order)
                .map(_order -> ResponseEntity.status(HttpStatus.CREATED).body(_order))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_WAITING_ORDER)
    public ResponseEntity<Order> getWaitingOrder(@PathVariable String idClient) {
        return service.getWaitingOrder(idClient)
                .map(_order -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_order))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

}

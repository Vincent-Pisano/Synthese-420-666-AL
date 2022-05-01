package com.synthese.shipping.controller;

import com.synthese.shipping.model.Order;
import com.synthese.shipping.service.ShippingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.synthese.shipping.utils.Utils.CROSS_ORIGIN_ALLOWED;
import static com.synthese.shipping.utils.Utils.ShippingControllerUrl.URL_HANDLE_ORDER;

@RestController
//@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class ShippingController {

    private final ShippingService service;

    public ShippingController(ShippingService service) {
        this.service = service;
    }

    @GetMapping(URL_HANDLE_ORDER)
    public ResponseEntity<Order> handleOrder(@PathVariable String orderID) {
        return service.handleOrder(orderID)
                .map(_order -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_order))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}

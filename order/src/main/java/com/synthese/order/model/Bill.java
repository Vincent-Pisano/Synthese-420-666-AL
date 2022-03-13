package com.synthese.order.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bill extends Entity {

    @DBRef
    private Order order;
    private Address address;

    public Order.OrderStatus getStatus() {
        return order != null ? order.getStatus() : null;
    }

}

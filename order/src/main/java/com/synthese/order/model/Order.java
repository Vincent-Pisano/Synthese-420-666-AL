package com.synthese.order.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends Entity{

    public enum OrderStatus {
        WAITING,
        CONFIRMED,
        IN_TRANSIT,
        DELIVERED,
        DELETED
    }

    @DBRef
    private Client client;
    private List<OrderItem> orderItems;
    private OrderStatus status;

    public Float getTotalPrice() {
        return this.orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(0f, Float::sum);
    }

}

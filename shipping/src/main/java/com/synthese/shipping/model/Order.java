package com.synthese.shipping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Order extends Entity{

    public enum OrderStatus {
        WAITING,
        CONFIRMED,
        SENT,
        DELETED
    }

    @DBRef
    private Client client;

    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder.Default
    private OrderStatus status = OrderStatus.WAITING;

    private Address shippingAddress;
    private Date shippingDate;

    public Float getTotalPrice() {
        return this.orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(0f, Float::sum);
    }

    public Order() {
        super();
        this.status = OrderStatus.WAITING;
        this.orderItems = new ArrayList<>();
    }

}

package com.synthese.order.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class OrderItem {

    @DBRef
    private Item item;
    private Integer quantity;

    public float getTotalPrice() {
        return item.getPrice() * quantity;
    }
}

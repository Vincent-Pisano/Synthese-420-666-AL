package com.synthese.order.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder(toBuilder = true)
public class OrderItem {

    @DBRef
    private Item item;
    private Integer quantity;

    public float getTotalPrice() {
        return item.getPrice() * quantity;
    }
}

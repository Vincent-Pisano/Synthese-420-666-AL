package com.synthese.shipping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @DBRef
    private Item item;
    private Integer quantity;

    public float getTotalPrice() {
        return item.getPrice() * quantity;
    }
}

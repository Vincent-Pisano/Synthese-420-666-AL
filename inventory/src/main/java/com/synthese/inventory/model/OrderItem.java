package com.synthese.inventory.model;

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

}

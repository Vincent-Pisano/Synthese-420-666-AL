package com.synthese.order.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Item extends Entity {

    public enum ItemCategory {
        OTHER,
        FOOD,
        CLOTHES,
        ELECTRONICS
    }

    public enum ItemStatus {
        HIDDEN,
        VISIBLE,
        DELETED
    }

    @Indexed(unique = true)
    private String name;
    private String description;
    private float price;
    private float beforeSalePrice;
    private boolean isOnSale;
    private int quantity;
    private ItemCategory category;
    private ItemStatus status;
    protected Binary image;


    public Item() {
        super();
        isOnSale = false;
        beforeSalePrice = price;
        status = ItemStatus.HIDDEN;
    }

}

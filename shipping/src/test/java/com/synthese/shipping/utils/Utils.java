package com.synthese.shipping.utils;

import com.synthese.shipping.model.Client;
import com.synthese.shipping.model.Item;
import com.synthese.shipping.model.Order;
import com.synthese.shipping.model.OrderItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static class ShippingControllerUrl {
        public final static String URL_HANDLE_ORDER = "/handle/order/";
    }

    public static final String ID = "62082f071f32b05b7b0706f1";

    public static Order getOrderWithIDAndOrderItems() throws IOException {
        return getOrderWithoutID()
                .toBuilder()
                .id(ID)
                .orderItems(getListOfOrderItems())
                .build();
    }

    public static List<OrderItem> getListOfOrderItems() throws IOException {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(getOrderItemWithID());
        orderItems.add(getOrderItemWithID());
        orderItems.forEach(orderItem -> orderItem.getItem().setImage(null));
        return orderItems;
    }

    public static OrderItem getOrderItemWithID() throws IOException {
        return OrderItem.builder()
                .item(getItemWithID())
                .quantity(2)
                .build();
    }

    public static Item getItemWithID() throws IOException {
        return getItemWithoutID()
                .toBuilder()
                .id(ID)
                .build();
    }

    public static Item getItemWithoutID() throws IOException {
        return Item.builder()
                .name("There are no accident")
                .description("master Oogway")
                .price(150.69f)
                .isOnSale(false)
                .beforeSalePrice(150.69f)
                .quantity(10)
                .category(Item.ItemCategory.OTHER)
                .status(Item.ItemStatus.HIDDEN)
                .build();
    }

    public static List<Item> getListOfItems() throws IOException {
        List<Item> items = new ArrayList<>();
        items.add(getItemWithID());
        items.add(getItemWithID());
        return items;
    }

    public static Order getOrderWithID() {
        return getOrderWithoutID()
                .toBuilder()
                .id(ID)
                .build();
    }

    public static Order getOrderWithoutID(){
        return Order.builder()
                .client(getClientWithID())
                .build();
    }

    public static Client getClientWithID() {
        return Client.builder()
                .firstName("un")
                .lastName("client")
                .email("client@test.com")
                .password("client1234")
                .id(ID)
                .build();
    }


}

package com.synthese.order.utils;

import com.synthese.order.model.Client;
import com.synthese.order.model.Item;
import com.synthese.order.model.Order;
import com.synthese.order.model.OrderItem;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static class OrderControllerUrl {
        public final static String URL_SAVE_ORDER = "/save/order";
        public final static String URL_CONFIRM_ORDER = "/confirm/order";
        public final static String URL_GET_WAITING_ORDER = "/get/order/waiting/";
        public final static String URL_GET_ALL_ORDER_OF_CLIENT = "/get/all/orders/";
    }

    public static final String ID = "62082f071f32b05b7b0706f1";

    public static List<Order> getOrderList() throws IOException {
        List<Order> orders = new ArrayList<>();
        orders.add(getOrderWithIDAndOrderItems());
        return orders;
    }

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

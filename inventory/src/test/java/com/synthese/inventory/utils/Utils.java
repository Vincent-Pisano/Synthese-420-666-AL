package com.synthese.inventory.utils;

import com.synthese.inventory.model.Item;
import com.synthese.inventory.model.OrderItem;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static class InventoryControllerUrl {
        public final static String URL_ADD_ITEM = "/add/item";
        public final static String URL_UPDATE_ITEMS_QUANTITY = "/update/items/quantity";
        public final static String URL_GET_ALL_ITEMS_FROM_CATEGORY = "/get/all/items/category/";
        public final static String URL_GET_VISIBLE_ITEMS_FROM_CATEGORY = "/get/visible/items/category/";
        public final static String URL_GET_IMAGE = "/get/image/";
    }

    public static final String ID = "62082f071f32b05b7b0706f1";
    public static final Item.ItemCategory CATEGORY_OTHER = Item.ItemCategory.OTHER;
    public final static String IMAGE_FILEPATH =
            System.getProperty("user.dir") + "\\src\\test\\resources\\assets\\image.jpg";

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
                .category(CATEGORY_OTHER)
                .status(Item.ItemStatus.HIDDEN)
                .image(getImage())
                .build();
    }

    public static List<Item> getListOfItems() throws IOException {
        List<Item> items = new ArrayList<>();
        items.add(getItemWithID());
        items.add(getItemWithID());
        return items;
    }

    public static Binary getImage() throws IOException {
        Path image = Paths.get(IMAGE_FILEPATH);
        return new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(image));
    }

}

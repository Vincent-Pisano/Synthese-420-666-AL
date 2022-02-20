package com.synthese.inventory.utils;

import com.synthese.inventory.model.Item;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static class InventoryControllerUrl {
        public final static String URL_ADD_ITEM = "/add/item";
    }

    public static final String ID = "62082f071f32b05b7b0706f1";
    public final static String IMAGE_FILEPATH =
            System.getProperty("user.dir") + "\\src\\test\\resources\\assets\\image.jpg";

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
                .image(getImage())
                .build();
    }

    public static Binary getImage() throws IOException {
        Path image = Paths.get(IMAGE_FILEPATH);
        return new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(image));
    }

}

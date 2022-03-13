package com.synthese.order.utils;

import com.synthese.order.model.Client;
import com.synthese.order.model.Item;
import com.synthese.order.model.Order;
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
        public final static String URL_GET_WAITING_ORDER = "/get/order/waiting/";
    }

    public static final String ID = "62082f071f32b05b7b0706f1";

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

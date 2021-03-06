package com.synthese.order.utils;

public class Utils {

    public final static String CROSS_ORIGIN_ALLOWED = "http://localhost:3000";

    public static class OrderControllerUrl {
        public final static String URL_SAVE_ORDER = "/save/order";
        public final static String URL_CONFIRM_ORDER = "/confirm/order";
        public final static String URL_GET_WAITING_ORDER = "/get/order/waiting/{idClient}";
        public final static String URL_GET_ALL_ORDER_OF_CLIENT = "/get/all/orders/{idClient}";
    }

    public static class InventoryControllerUrl {
        public final static String URL_UPDATE_ITEM_QUANTITY = "http://host.docker.internal:7870/update/items/quantity";
    }

    public static class ShippingControllerUrl {
        public final static String URL_HANDLE_ORDER = "http://host.docker.internal:7872/handle/order/";
    }
}

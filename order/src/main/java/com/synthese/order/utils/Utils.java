package com.synthese.order.utils;

public class Utils {

    public final static String CROSS_ORIGIN_ALLOWED = "http://localhost:3000";

    public static class OrderControllerUrl {
        public final static String URL_SAVE_ORDER = "/save/order";
        public final static String URL_GET_WAITING_ORDER = "/get/order/waiting/{idClient}";
    }
}

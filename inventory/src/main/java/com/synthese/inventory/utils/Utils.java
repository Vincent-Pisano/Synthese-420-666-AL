package com.synthese.inventory.utils;

public class Utils {

    public final static String CROSS_ORIGIN_ALLOWED = "http://localhost:3000";
    public final static String APPLICATION_JSON_AND_CHARSET_UTF8 = "application/json;charset=utf8";
    public final static String MULTI_PART_FROM_DATA = "multipart/form-data";
    public final static String REQUEST_PART_IMAGE = "image";
    public final static String REQUEST_PART_ITEM = "item";

    public static class InventoryControllerUrl {
        public final static String URL_ADD_ITEM = "/add/item";
        public final static String URL_GET_ITEMS_FROM_CATEGORY = "/get/items/category/{category}";
        public final static String URL_GET_IMAGE = "/get/image/{id}";
    }

}

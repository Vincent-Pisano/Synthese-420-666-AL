package com.synthese.auth.utils;

public class Utils {

    public final static String CROSS_ORIGIN_ALLOWED = "http://localhost:3000";

    public static class AuthControllerUrl {
        public final static String URL_SIGN_UP_CLIENT = "/signUp";
        public final static String URL_LOGIN_CLIENT = "/login/client/{email}/{password}";
        public final static String URL_LOGIN_ADMIN = "/login/admin/{email}/{password}";
    }

}

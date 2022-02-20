package com.synthese.auth.utils;

import com.synthese.auth.model.Admin;
import com.synthese.auth.model.Client;

public class Utils {

    public static class AuthControllerUrl {
        public final static String URL_SIGN_UP_CLIENT = "/signUp";
        public final static String URL_LOGIN_CLIENT = "/login/client/";
        public final static String URL_LOGIN_ADMIN = "/login/admin/";
    }

    public static final String ID = "62082f071f32b05b7b0706f1";

    public static Client getClientWithID() {
        return getClientWithoutID().toBuilder()
                .id(ID)
                .build();
    }

    public static Client getClientWithoutID() {
        return Client.builder()
                .firstName("un")
                .lastName("client")
                .email("client@test.com")
                .password("client1234")
                .build();
    }

    public static Admin getAdminWithID() {
        return getAdminWithoutID().toBuilder()
                .id(ID)
                .build();
    }

    public static Admin getAdminWithoutID() {
        return Admin.builder()
                .firstName("un")
                .lastName("admin")
                .email("admin@test.com")
                .password("admin1234")
                .build();
    }

}

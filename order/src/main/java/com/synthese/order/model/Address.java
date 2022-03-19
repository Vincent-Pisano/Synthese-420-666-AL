package com.synthese.order.model;

import lombok.Data;

@Data
public class Address {

    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String apartmentCode;

}

package com.synthese.shipping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String apartmentCode;

}

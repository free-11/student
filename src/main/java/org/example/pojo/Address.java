package org.example.pojo;

import lombok.Data;

@Data
public class Address {
    private Integer addressId;
    private String province;
    private String city;
    private String district;
    private String detail;
}

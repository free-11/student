package org.example.service;

import org.example.pojo.Address;

import java.util.List;

public interface AddressService {
    void add(Address address);
    List<Address> findAll();
}

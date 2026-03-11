package org.example.service.impl;

import org.example.mapper.AddressMapper;
import org.example.pojo.Address;
import org.example.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void add(Address address) {
        addressMapper.add(address);
    }

    @Override
    public List<Address> findAll() {
        return addressMapper.findAll();
    }
}

package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Address;

import java.util.List;

@Mapper
public interface AddressMapper {
    void add(Address address);
    List<Address> findAll();
}

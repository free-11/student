package org.example.controller;

import org.example.pojo.Address;
import org.example.pojo.Result;
import org.example.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public Result add(@RequestBody Address address) {
        addressService.add(address);
        return Result.success(address);
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<Address> list = addressService.findAll();
        return Result.success(list);
    }
}

package org.example.service.impl;

import org.example.mapper.MajorMapper;
import org.example.pojo.Major;
import org.example.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public List<Major> findAll() {
        return majorMapper.findAll();
    }
}

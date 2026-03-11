package org.example.service.impl;

import org.example.mapper.CollegeMapper;
import org.example.pojo.College;
import org.example.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public List<College> findAll() {
        return collegeMapper.findAll();
    }
}

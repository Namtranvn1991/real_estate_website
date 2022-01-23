package com.c0721g2srsrealestatebe.service.employee.impl;


import com.c0721g2srsrealestatebe.model.employee.Position;
import com.c0721g2srsrealestatebe.repository.employee.IPositionRepository;
import com.c0721g2srsrealestatebe.service.employee.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements IPositionService {
    @Autowired
    IPositionRepository iPositionRepository;

    @Override
    public List<Position> findAll() {
        return this.iPositionRepository.findAll();
    }
}

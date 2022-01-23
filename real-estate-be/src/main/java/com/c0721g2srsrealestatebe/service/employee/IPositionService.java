package com.c0721g2srsrealestatebe.service.employee;

import com.c0721g2srsrealestatebe.model.employee.Degree;
import com.c0721g2srsrealestatebe.model.employee.Position;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPositionService {

    public List<Position> findAll();

}

package com.c0721g2srsrealestatebe.service.employee.impl;

import com.c0721g2srsrealestatebe.model.employee.Degree;
import com.c0721g2srsrealestatebe.model.employee.Employee;
import com.c0721g2srsrealestatebe.repository.employee.IEmployeeRepository;
import com.c0721g2srsrealestatebe.service.employee.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Override
    public void saveEmployee(Employee employee) {
        iEmployeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findById(String id) {
        return iEmployeeRepository.findById(id);
    }

    @Override
    public Page<Employee> findAllEmployeeSearch(Pageable pageable, String name, String email, String position) {
        return this.iEmployeeRepository.searchEmployeeByNameOrEmailOrDegree(pageable, name, email, position);
    }

    @Override
    public Page<Employee> findAllEmployeePage(Pageable pageable) {
        return this.iEmployeeRepository.findAllEmployee(pageable);
    }

    @Override
    public void deleteById(String id) {
        this.iEmployeeRepository.deleteById(id);
    }

    @Override
    public Optional<Employee> findByIdOp(String id) {
        return this.iEmployeeRepository.findById(id);
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return this.iEmployeeRepository.findEmployeeByAppUser(username);
    }
}
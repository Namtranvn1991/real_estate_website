package com.c0721g2srsrealestatebe.service.employee;

import com.c0721g2srsrealestatebe.model.employee.Degree;
import com.c0721g2srsrealestatebe.model.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    void saveEmployee(Employee employee);

    Optional<Employee> findById(String id);

    public Page<Employee> findAllEmployeeSearch(Pageable pageable, String name, String email, String position_id);

    public Page<Employee> findAllEmployeePage(Pageable pageable);

    void deleteById(String id);

    Optional<Employee> findByIdOp(String id);

    Employee getEmployeeByUsername(String username);
}

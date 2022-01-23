package com.c0721g2srsrealestatebe.service.customer;

import com.c0721g2srsrealestatebe.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    Iterable<Customer> findAll();

    Optional<Customer> findById(String id);

    Customer save(Customer customer);

    void removeCustomer(String id);

    List<Customer> searchByName(String name);

    // thienlb
    Page<Customer> findAllCustomerByNameAndPhoneAndEmailPage(String name, String phone, String email, Pageable pageable);

    //phuong thuc nay cua Hien
    void saveCustomerSocial(Customer customer);

    Customer getCustomerByUsername(String username);
}
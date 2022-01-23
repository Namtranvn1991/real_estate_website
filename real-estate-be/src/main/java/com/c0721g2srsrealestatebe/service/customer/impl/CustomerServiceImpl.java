package com.c0721g2srsrealestatebe.service.customer.impl;


import com.c0721g2srsrealestatebe.model.customer.Customer;
import com.c0721g2srsrealestatebe.repository.customer.ICustomerRepository;
import com.c0721g2srsrealestatebe.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    ICustomerRepository iCustomerRepository;


    @Override
    public Iterable<Customer> findAll() {
        return iCustomerRepository.findAll();
    }


    // dùng chung
    @Override
    public Optional<Customer> findById(String id) {
        return iCustomerRepository.findById(id);
    }


    // Dùng chung
    public Customer save(Customer customer) {
        return iCustomerRepository.save(customer);
    }

    // Tùng
    public Boolean existByEmail(String email){
        return iCustomerRepository.existsByEmail(email);
    }

    //thienlb
    @Override
    public void removeCustomer(String id) {
        iCustomerRepository.updateCustomer(id);
    }
    // thienlb
    @Override
    public Page<Customer> findAllCustomerByNameAndPhoneAndEmailPage(String name, String phone, String email
            , Pageable pageable) {
        return iCustomerRepository.findAllCustomerByNameAndPhoneAndEmail(
                name, phone, email, (org.springframework.data.domain.Pageable) pageable);
    }


    @Override
    public List<Customer> searchByName(String name) {
        return null;
    }

    //phuong thuc nay cua Hien
    @Override
    public void saveCustomerSocial(Customer customer) {
        this.iCustomerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return this.iCustomerRepository.findCustomerByAppUser(username);
    }
}
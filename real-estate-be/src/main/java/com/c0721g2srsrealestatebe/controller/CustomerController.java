package com.c0721g2srsrealestatebe.controller;


import com.c0721g2srsrealestatebe.dto.CustomerDTO;
import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.account.Role;
import com.c0721g2srsrealestatebe.model.customer.Customer;
import com.c0721g2srsrealestatebe.service.account.impl.AppUserServiceImpl;
import com.c0721g2srsrealestatebe.service.customer.impl.CustomerServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;


import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    AppUserServiceImpl appUserService;


    // TungLe tìm kiếm khách hàng
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable String id) {
        Optional<Customer> customer = customerService.findById(id);
        if (!customer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    }



    // TungLe thêm mới khách hàng
    @PostMapping(value = "/create")
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        new CustomerDTO().validate(customerDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        System.out.println(customer);
        Map<String,String> listErrors = new HashMap<>();


        //set role
        Role role = new Role();
        role.setId((long) 3);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        // tạo account
        AppUser appUser = new AppUser();
        if (appUserService.existsByUserName(customerDTO.getUserName())) {
            System.out.println("Error");
            listErrors.put("errorUsername","Tài khoản đã được đăng kí ");
            return ResponseEntity.badRequest().body(listErrors);
        }

        if(customerService.existByEmail(customerDTO.getEmail())){
            System.out.println("Error");
            listErrors.put("errorEmail","Email đã được đăng kí ");
            return ResponseEntity.badRequest().body(listErrors);
        }



//        appUser.setUsername(appUser.getUsername());
//        appUser.setPassword(appUser.getPassword());
//        appUser.setUsername(customerDTO.getUserName());
//        appUser.setPassword(customerDTO.getPassword());
//        appUser.getPassword();
//        appUser.getUsername();
        appUser.setPassword(customerDTO.getPassword());
        appUser.setUsername(customerDTO.getUserName());
        appUser.setRoles(roleSet);

        customer.setAppUser(appUser);
        System.out.println(appUser);
        customerService.save(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Iterable<Customer>> showListCustomer() {
        List<Customer> customers = (List<Customer>) customerService.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }




    //    ThienLb - tim kiem + phan trang
    @GetMapping("/customer-list")
    public ResponseEntity<Page<Customer>> findCustomerByPhoneAndNameAndEmail(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id"));
        Page<Customer> customersNewPage = customerService.findAllCustomerByNameAndPhoneAndEmailPage(name, phone, email, pageable);

        if (customersNewPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customersNewPage, HttpStatus.OK);

    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Customer> findCustomerById(@PathVariable String id) {
//        Optional<Customer> customerOptional = customerService.findCustomerById(id);
//        if (!customerOptional.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
//    }

    //thienlb-xoa khach hang
    @DeleteMapping("delete-customer/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.removeCustomer(id);
        return new ResponseEntity<>(customerOptional.get(), HttpStatus.NO_CONTENT);
    }

    //thiện nhỏ-edit customer
    @PatchMapping(value = "/edit-customer/{id}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<Customer> update(@Valid @RequestBody CustomerDTO customerDTO, BindingResult bindingResult,@PathVariable String id) {
        try {
            new CustomerDTO().validate(customerDTO, bindingResult);
            if (bindingResult.hasFieldErrors("name")) {
                System.out.println("tên bạn nhập không đúng");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (bindingResult.hasFieldErrors("idCard")) {
                System.out.println("card bạn nhập không đúng");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerDTO.setId(id);
            customerService.findById(customerDTO.getId());
            customerDTO.toString();
            Customer customer1 = new Customer();
            System.out.println(customerDTO.toString());
            BeanUtils.copyProperties(customerDTO, customer1);

            customerService.save(customer1);
            return new ResponseEntity<>(customer1, HttpStatus.OK);
        } catch (BeansException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

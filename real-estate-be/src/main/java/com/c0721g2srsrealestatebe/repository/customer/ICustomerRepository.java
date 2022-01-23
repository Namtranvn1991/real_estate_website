package com.c0721g2srsrealestatebe.repository.customer;


import com.c0721g2srsrealestatebe.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.c0721g2srsrealestatebe.model.account.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, String> {

    //    thienlb
    @Query(value = "select customers.id, customers.address, customers.date_of_birth,\n" +
            " customers.deleted, customers.email, customers.gender,\n" +
            " customers.`name`, customers.phone_number,  customers.app_user_id, \n" +
            " images.url, customers.image_id\n" +
            "from customers join images on customers.image_id = images.id\n" +
            "join app_users on customers.app_user_id = app_users.id\n" +
            "where customers.deleted = 0;", nativeQuery = true)
    Page<Customer> findAllCustomerByNative(Pageable pageable);


    //thienlb - hien thi + search
    @Query(value = "select * from customers where customers.deleted = 0 and customers.`name` like concat('%',:name,'%') \n" +
            "and customers.phone_number like concat('%',:phone,'%') and customers.email like concat('%',:email,'%')", nativeQuery = true,
            countQuery = "select count(*) from customers \n" +
                    " where customers.deleted = 0 \n" +
                    "and customers.`name` like concat('%',:name,'%') \n" +
                    "and customers.phone_number like concat('%',:phone,'%') \n" +
                    "and customers.email like concat('%',:email,'%')")
    Page<Customer> findAllCustomerByNameAndPhoneAndEmail(@Param("name") String name,
                                                         @Param("phone") String phone,
                                                         @Param("email") String email,
                                                         @Param("page") Pageable pageable);

    // Xoa KH Thiện LB

    @Modifying
    @Transactional
    @Query(value = "update customers a set a.deleted= true where a.id = :id", nativeQuery = true)
    void updateCustomer(@Param("id") String id);


    // Tùng thêm mới customer
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO customers (customers.address, customers.date_of_birth, " +
            "customers.deleted, customers.email," +
            " customers.gender,customers.id_card, customers.`name`, customers.phone_number," +
            " customers.app_user_id, customers.image_id)" +
            " VALUES (:id,:address,:date_of_birth,:deleted,:email,:gender,:idCard,:name,:phone_number,:appUser)", nativeQuery = true)
    void save(@Param("address") String address, @Param("date_of_birth") LocalDate date_of_birth, @Param("deleted")
            Boolean deleted, @Param("email") String email, @Param("gender") Integer gender, @Param("idCard") String idCard, @Param("name") String name,
              @Param("phone_number") String phone_number, @Param("appUser") AppUser appUser);


    @Query(value = "SELECT * FROM customers WHERE customers.id = :id", nativeQuery = true)
    Optional<Customer> findById(@Param("id") String id);


    // Tùng kiểm tra email
    @Query
    Boolean existsByEmail(String email);

    @Query(value = "SELECT * from customers c join app_users a on c.app_user_id = a.id where a.username =?1", nativeQuery = true)
    Customer findCustomerByAppUser(String username);
}
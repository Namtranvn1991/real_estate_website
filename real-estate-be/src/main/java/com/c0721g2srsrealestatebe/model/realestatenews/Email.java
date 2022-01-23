package com.c0721g2srsrealestatebe.model.realestatenews;

public class Email {
   private String customerMail;
   private String name;
   private String phone;

    public Email() {
        //this is constructor
    }

    public Email(String customerMail, String name, String phone) {
        this.customerMail = customerMail;
        this.name = name;
        this.phone = phone;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerId) {
        this.customerMail = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

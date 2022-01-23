//package com.c0721g2srsrealestatebe.validation;
//
//import com.c0721g2srsrealestatebe.dto.CustomerDTO;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import java.time.LocalDate;
//import java.time.Period;
//
//public class AgeValidation implements ConstraintValidator <Age, CustomerDTO> {
//    @Override
//    public void initialize(Age constraintAnnotation) {
//
//    }
//
//    @Override
//    public boolean isValid(CustomerDTO customerDTO, ConstraintValidatorContext context) {
//        Period p= Period.between(customerDTO.getDateOfBirth(), LocalDate.now());
//        return p.getYears()==customerDTO.getAge();
//    }
//}

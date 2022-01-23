package com.c0721g2srsrealestatebe.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import javax.validation.constraints.*;
import java.time.LocalDate;

// ThienND & TungLQ dùng chung Validate này
public class CustomerDTO implements Validator {


    private String id;

    @NotBlank(message = "you have to input your name")
//    @Size(min = 2, message = "Tên ít nhất phải 2 ký tự")
    @Pattern(regexp = "^([^0-9]{2,})$", message = "Tên không được có số và từ 2 kí tự trở lên")
    private String name;


    @NotNull(message = "Không được bỏ trống")
//    @Pattern(regexp = "^(?:19\\d{2}|20\\d{2})[-/.](?:0[1-9]|1[012])[-/.](?:0[1-9]|[12][0-9]|3[01])$",
//            message = "Ngày sinh phải đúng định dạng: dd/MM/yyyy.")
    private LocalDate dateOfBirth;


    @NotBlank(message = "Số CMND không được để trống.")
    @Pattern(regexp = "^([0-9]{9})$|([0-9]{12})$",
            message = "Số CMND phải đúng định dạng: XXXXXXXXX hoặc XXXXXXXXXXXX.")
    private String idCard;


    @NotBlank
    private String address;


    @NotBlank(message = "Số điện thoại không được để trống.")
//    @Pattern(regexp = "^(0[0-9\\s.-]{9,13})$",
//            message = "Số điện thoại phải đúng định dạng: 090xxxxxxx hoặc 091xxxxxxx hoặc (84)+90xxxxxxx hoặc (84)+91xxxxxxx")
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống.")
    @Email
    private String email;

    @NotNull
    private Integer gender;

    private AppUserDTO appUserDTO;
    private ImageDTO image;

    private Long roleDTO;

    @NotEmpty
    private String userName;

    @NotEmpty
    @Size(min = 5, max = 250)
    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(Long roleDTO) {
        this.roleDTO = roleDTO;
    }

    public AppUserDTO getAppUserDTO() {
        return appUserDTO;
    }

    public void setAppUserDTO(AppUserDTO appUserDTO) {
        this.appUserDTO = appUserDTO;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

    private Boolean deleted = Boolean.FALSE;

    public CustomerDTO() {
        // constructor no param
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    //    public int getAge() {
//        return Age;
//    }
//
//    public void setAge(int age) {
//        Age = age;
//    }
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    private boolean checkIdCard;
    private boolean checkPhone;


    public boolean isCheckIdCard() {
        return checkIdCard;
    }

    public void setCheckIdCard(boolean checkIdCard) {
        this.checkIdCard = checkIdCard;
    }

    public boolean isCheckPhone() {
        return checkPhone;
    }

    public void setCheckPhone(boolean checkPhone) {
        this.checkPhone = checkPhone;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", idCard='" + idCard + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", appUser=" + appUserDTO +
                ", image=" + image +
                ", deleted=" + deleted +
                ", checkIdCard=" + checkIdCard +
                '}';
    }

}
package com.c0721g2srsrealestatebe.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AppUserDTO implements Validator {
    private String username;
    //    @Size(min = 8, max = 12)
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$" ,
//            message = "Mật khẩu phải ít nhất có 1 ký tự hoa và thường và 1 số")
    private String password;
    private String newPassword;
    private String reNewPassword;


    public AppUserDTO() {
    }

    public AppUserDTO(String username, String password, String newPassword, String reNewPassword) {
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
        this.reNewPassword = reNewPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReNewPassword() {
        return reNewPassword;
    }

    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }

    @Override
    public String toString() {
        return "AppUserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", reNewPassword='" + reNewPassword + '\'' +
                '}';
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
//chứa đoạn code validate
    }
}
package com.c0721g2srsrealestatebe.service.account;

import com.c0721g2srsrealestatebe.dto.AppUserDTO;
import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.employee.Employee;
import com.c0721g2srsrealestatebe.payload.request.CustomerSocial;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


public interface IAppUserService {
    AppUser getAppUserByEmail(String email);

    boolean existsUserByEmail(String email);

    void addVerificationCode(String email) throws MessagingException, UnsupportedEncodingException;

    void saveNewPassword(String passwordEncode, String code);

    Boolean findUserByVerificationCode(String code);

    AppUser createCustomerSocial(CustomerSocial customerSocial);

    boolean existsByUserName(String username);

    void updatePassword(AppUserDTO appUserDTO);

    String findPasswordByUsername(String username);

    AppUser findAppUserByUserName(String id);

    AppUser getAppUserByEmployee(String id);
}
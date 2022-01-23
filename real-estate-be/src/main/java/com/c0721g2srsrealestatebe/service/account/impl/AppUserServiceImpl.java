package com.c0721g2srsrealestatebe.service.account.impl;

import com.c0721g2srsrealestatebe.Exception.AppUserException;
import com.c0721g2srsrealestatebe.dto.AppUserDTO;
import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.account.Role;
import com.c0721g2srsrealestatebe.model.customer.Customer;
import com.c0721g2srsrealestatebe.model.employee.Employee;
import com.c0721g2srsrealestatebe.model.image.Image;
import com.c0721g2srsrealestatebe.payload.request.CustomerSocial;
import com.c0721g2srsrealestatebe.repository.account.IAppUserRepository;
import com.c0721g2srsrealestatebe.service.account.IAppUserService;
import com.c0721g2srsrealestatebe.service.customer.impl.CustomerServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AppUserServiceImpl implements IAppUserService {

    @Autowired
    private IAppUserRepository appUserRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private CustomerServiceImpl customerService;


    @Override
    public AppUser getAppUserByEmail(String email) {
        return appUserRepository.getAppUserByEmailCustomer(email);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return appUserRepository.existsUserByEmail(email) != null;
    }

    @Override
    public void addVerificationCode(String email) throws MessagingException, UnsupportedEncodingException {
        String username = appUserRepository.existsUserByEmail(email);
        String code = RandomString.make(64);
        appUserRepository.addVerificationCode(code, username);
        this.sendVerificationEmailForResetPassWord(username, code, email);
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
            appUserRepository.deleteVerificationCode(username);
        });
    }

    @Override
    public void saveNewPassword(String passwordEncode, String code) {
        appUserRepository.saveNewPassword(passwordEncode, code);
        appUserRepository.deleteVerificationCode(code);
    }

    @Override
    public Boolean findUserByVerificationCode(String code) {
        return appUserRepository.findUserByVerificationCode(code) != null;
    }

    @Override
    public AppUser createCustomerSocial(CustomerSocial customerSocial) {
        Customer customer = new Customer();
        customer.setName(customerSocial.getName());
        customer.setEmail(customerSocial.getEmail());

        Image image = new Image();
        image.setUrl(customerSocial.getUrlImg());
        customer.setImage(image);

        AppUser appUser = new AppUser();
        appUser.setUsername(customerSocial.getEmail());
        appUser.setPassword(customerSocial.getPassword());
        appUser.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        Role role = roleService.findRoleByName("ROLE_CUSTOMER");
        roles.add(role);

        appUser.setRoles(roles);

        customer.setAppUser(appUser);
        this.customerService.saveCustomerSocial(customer);
        return appUser;
    }


    public void sendVerificationEmailForResetPassWord(String userName, String randomCode, String email) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email xác thực!";
        String mailContent = "";
        String confirmUrl = "http://localhost:4200/security/verify-reset-password?code=" + randomCode;


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom("plthienbkdn@gmail.com", "Bất động sản Hưng Thịnh Group");
        helper.setTo(email);
        helper.setSubject(subject);
        mailContent = "<p sytle='color:red;'>Xin chào " + userName + " ,<p>" + "<p> Nhấn vào link sau để thay đổi mật khẩu của bạn:</p>" +
                "<h3><a href='" + confirmUrl + "'>Link Xác thực( nhấn vào đây)!</a></h3>" +
                "<p>-----------------------------------------------------------</p>" +
                "<p>Bất động sản Hưng Thịnh Group</p>" +
                "<p>Địa chỉ: Tòa nhà Hưng Thịnh Group, số 99 đường Lê Duẩn</p>" +
                "<p>Email: plthienbkdn@gmail.com</p>" +
                "<p>Số điện thoại: 0905686868</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);

    }

    // Tùng kiểm tra username
    public boolean existsByUserName(String username) {
        return appUserRepository.existsByUsername(username);
    }


    @Override
    public String findPasswordByUsername(String username) {
        return appUserRepository.findPasswordByUsername(username);
    }


    @Override
    public void updatePassword(AppUserDTO appUserDTO) {
        appUserRepository.saveNewPassword(appUserDTO.getPassword(), appUserDTO.getUsername());
    }

    @Override
    public AppUser findAppUserByUserName(String id) {
        return appUserRepository.findAppUserByUsername(id).orElseThrow(() -> new AppUserException(
                "không thể tìm thấy id " + id + ""));
    }

    @Override
    public AppUser getAppUserByEmployee(String id) {
        return appUserRepository.findByEmployee(id);
    }
}
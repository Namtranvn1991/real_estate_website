package com.c0721g2srsrealestatebe.service.realestatenews.impl;

import com.c0721g2srsrealestatebe.service.realestatenews.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String customerEmail, String name, String phone) throws MessagingException, UnsupportedEncodingException {
        String mailContent = "";
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom("plthienbkdn@gmail.com", "Bất động sản Hưng Thịnh Group");
        helper.setTo(customerEmail);
        helper.setSubject("Một khách hàng quan tâm tới bài đăng của bạn");
        mailContent = "Chào bạn \n" +
                "Khách hàng" + name + " số điện thoại " + phone + " đang quan tâm đến bài đăng của bạn tên trang \n" +
                "hungthinhgroup.com.\n" +
                "Thanks and Regards\n" +
                "<p>------------------------------------------</p>" +
                "<p>Bất động sản Hưng Thịnh Group</p>" +
                "<p>Địa chỉ: Tòa nhà Hưng Thịnh Group, số 99 đường Lê Duẩn</p>" +
                "<p>Email: plthienbkdn@gmail.com</p>" +
                "<p>Số điện thoại: 0905686868</p>";
        helper.setText(mailContent, true);
        emailSender.send(message);
    }
}

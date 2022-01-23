package com.c0721g2srsrealestatebe.service.realestatenews.impl;

import com.c0721g2srsrealestatebe.service.realestatenews.IApprovalMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class ApprovalMailServiceImpl implements IApprovalMailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendApprovalMail(String customerEmail, String status, String reason) throws MessagingException, UnsupportedEncodingException {
        String mailContent = "";
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom("plthienbkdn@gmail.com", "Bất động sản Hưng Thịnh Group");
        helper.setTo(customerEmail);
        helper.setSubject("Thông báo duyệt Bài đăng");
        mailContent = "<p>Chào bạn </p> \n" +
                "Bài đăng của bạn : " + status + " " + reason + "\n" +
                "<p>Thanks and Regards</p>" +
                "<p>------------------------------------------</p>" +
                "<p>Bất động sản Hưng Thịnh Group</p>" +
                "<p>hungthinhgroup.com.</p>\n" +
                "<p>Địa chỉ: Tòa nhà Hưng Thịnh Group, số 99 đường Lê Duẩn</p>" +
                "<p>Email: plthienbkdn@gmail.com</p>" +
                "<p>Số điện thoại: 0905686868</p>";
        helper.setText(mailContent, true);
        emailSender.send(message);
    }

}

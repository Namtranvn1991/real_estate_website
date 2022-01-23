package com.c0721g2srsrealestatebe.service.realestatenews;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IApprovalMailService {
    void sendApprovalMail(String customerEmail, String status, String reason)
            throws MessagingException, UnsupportedEncodingException;

}

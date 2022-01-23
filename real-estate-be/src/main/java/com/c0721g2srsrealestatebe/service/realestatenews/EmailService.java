package com.c0721g2srsrealestatebe.service.realestatenews;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendSimpleMessage(String customerEmail, String name, String phone) throws MessagingException, UnsupportedEncodingException;
}

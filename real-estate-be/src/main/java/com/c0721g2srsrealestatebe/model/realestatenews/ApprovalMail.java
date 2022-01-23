package com.c0721g2srsrealestatebe.model.realestatenews;

public class ApprovalMail {
    private String customerEmail;
    private String status;
    private String reason;

    public ApprovalMail() {
    }

    public ApprovalMail(String customerEmail, String status, String reason) {
        this.customerEmail = customerEmail;
        this.status = status;
        this.reason = reason;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

package com.c0721g2srsrealestatebe.payload.response;

import java.util.List;

public class JwtResponse {

    private String jwtToken;
    private String username;
    private String name;
    private String email;
    private String idCustomer;
    private String urlImg;
    private List<String> roles;

    public JwtResponse() {
    }

    public JwtResponse(String jwtToken, String username, String name, String email, String idCustomer, String urlImg, List<String> roles) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.name = name;
        this.email = email;
        this.idCustomer = idCustomer;
        this.urlImg = urlImg;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

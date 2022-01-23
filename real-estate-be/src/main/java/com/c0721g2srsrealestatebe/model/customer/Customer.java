package com.c0721g2srsrealestatebe.model.customer;

import com.c0721g2srsrealestatebe.customid.CustomIdGenerator;
import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.image.Image;
import com.c0721g2srsrealestatebe.model.realestatenews.RealEstateNews;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "customers")
@SQLDelete(sql = "UPDATE customers SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @GenericGenerator(
            name = "customer_seq",
            strategy = "com.c0721g2srsrealestatebe.customid.CustomIdGenerator",
            parameters = {
                    @Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.VALUE_PREFIX_PARAMETER, value = "KH-"),
                    @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")})
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String idCard;
    @Column(name = "date_of_Birth", columnDefinition = "DATE")
    private LocalDate dateOfBirth;
    @Column(name = "gender", columnDefinition = "TINYINT")
    private Integer gender;
    @OneToOne(targetEntity = AppUser.class, cascade = CascadeType.ALL)
    private AppUser appUser;
    @OneToOne(targetEntity = Image.class, cascade = CascadeType.PERSIST)
    private Image image;
    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customers_real_estate_news")
    private List< RealEstateNews > realEstateNewsList;

    private Boolean deleted = Boolean.FALSE;

    public Customer() {
    }
    @SuppressWarnings("squid:S00107")
    public Customer(String id, String name, String email, String phoneNumber, String address, String idCard, LocalDate dateOfBirth, Integer gender, AppUser appUser, Image image, List<RealEstateNews> realEstateNewsList, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.idCard = idCard;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.appUser = appUser;
        this.image = image;
        this.realEstateNewsList = realEstateNewsList;
        this.deleted = deleted;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<RealEstateNews> getRealEstateNewsList() {
        return realEstateNewsList;
    }

    public void setRealEstateNewsList(List<RealEstateNews> realEstateNewsList) {
        this.realEstateNewsList = realEstateNewsList;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", idCard='" + idCard + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", appUser=" + appUser +
                ", image=" + image +
                ", realEstateNewsList=" + realEstateNewsList +
                ", deleted=" + deleted +
                '}';
    }
}
package com.c0721g2srsrealestatebe.model.employee;

import com.c0721g2srsrealestatebe.customid.CustomIdGenerator;
import com.c0721g2srsrealestatebe.model.account.AppUser;
import com.c0721g2srsrealestatebe.model.image.Image;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "employees")
@SQLDelete(sql = "UPDATE employees SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @GenericGenerator(
            name = "employee_seq",
            strategy = "com.c0721g2srsrealestatebe.customid.CustomIdGenerator",
            parameters = {
                    @Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.VALUE_PREFIX_PARAMETER, value = "NV-"),
                    @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")})
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    @Column(name = "date_of_Birth", columnDefinition = "DATE")
    private LocalDate dateOfBirth;
    private String idCard;
    @Column(name = "gender", columnDefinition = "TINYINT")
    private Integer gender;
    @ManyToOne(targetEntity = Degree.class)
    private Degree degree;
    @ManyToOne(targetEntity = Position.class)
    private Position position;
    @OneToOne(targetEntity = AppUser.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private AppUser appUser;
    @OneToOne(targetEntity = Image.class)
    private Image image;
    private Boolean deleted = Boolean.FALSE;

    public Employee() {
    }

    @SuppressWarnings("squid:S00107")
    public Employee(String id, String name, String email, String phoneNumber, String address, LocalDate dateOfBirth, String idCard, Integer gender, Degree degree, Position position, AppUser appUser, Image image, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.idCard = idCard;
        this.gender = gender;
        this.degree = degree;
        this.position = position;
        this.appUser = appUser;
        this.image = image;
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

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", idCard='" + idCard + '\'' +
                ", gender=" + gender +
                ", degree=" + degree +
                ", position=" + position +
                ", appUser=" + appUser +
                ", image=" + image +
                ", deleted=" + deleted +
                '}';
    }
}

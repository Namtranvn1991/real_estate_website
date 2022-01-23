package com.c0721g2srsrealestatebe.model.account;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "app_users")
@SQLDelete(sql = "UPDATE app_users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class AppUser {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String username;
    private String password;
    private Boolean isEnabled;
    private String verificationCode;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST,targetEntity = Role.class)
    private Set<Role> roles;
    private Boolean deleted = Boolean.FALSE;

    public AppUser() {
    }

    public AppUser(String id, String username, String password, Boolean isEnabled, String verificationCode, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.verificationCode = verificationCode;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", verificationCode='" + verificationCode + '\'' +
                ", roles=" + roles +
                '}';
    }
}

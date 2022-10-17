package com.docsupport.jp.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table (name = "token")
public class PasswordResetToken {

    public static final int EXPIRATION = 20 * 60 * 1000; // 20 Mins
    public static final int MAX_ATTEMPTS = 10; // 20 Mins

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    private String token;

    @NotBlank(message = "userName is mandatory")
    @Column(name = "userName", unique = true, nullable = false, length = 40)
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Date expiryDate;

    @JsonIgnore
    public Integer getInvalidAttempts() {
        return invalidAttempts;
    }

    public void setInvalidAttempts(Integer invalidAttempts) {
        this.invalidAttempts = invalidAttempts;
    }

    private Integer invalidAttempts;
}
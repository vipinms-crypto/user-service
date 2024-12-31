package com.user.userservice.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(length = 50)
    private String userName;

    @Column(length = 50)
    private String userPassword;

    @Column(length = 50)
    private String userEmail;

    @Column(length = 50)
    private String userPhone;

    @Column(length = 50)
    private String userAddressLine1;

    @Column(length = 50)
    private String userAddressLine2;

    private Integer userIsRegistered;

    @Column(length = 50)
    private String userFirstName;

    @Column(length = 50)
    private String userMiddleName;

    @Column(length = 50)
    private String userLastName;

    @Column(length = 50)
    private String userCreatedBy;

    @Temporal(TemporalType.DATE)
    private Date userCreatedDatetime;

    @Column(length = 50)
    private String userUpdatedBy;

    @Temporal(TemporalType.DATE)
    private Date userUpdatedDatetime;
}

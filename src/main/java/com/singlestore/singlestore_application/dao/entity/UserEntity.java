package com.singlestore.singlestore_application.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "USER_FACT")
@ToString
/** The User fact entity */
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "user_state", nullable = false)
    private String userState;

    @Column(name = "user_login_time", nullable = false)
    private Timestamp userLoginTime;

    @Column(name = "refresh_duration", nullable = false)
    private int refreshDuration;

    @Column(name = "token", nullable = false)
    private String userAccessTokens;

}
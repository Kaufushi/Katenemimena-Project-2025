package com.gr.hua.dit.project2025.StreetFoodGo.core.model;

import java.time.Instant;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String username;

    @Enumerated(EnumType.STRING)
    private PersonType type;

    private String emailAddress;
    private String mobileNumber;
    private String physicalAddress;

    private Instant createdAt;
    private String passwordHash;


    public Person() {}

    public Person(Instant createdAt, PersonType type, String physicalAddress, String passwordHash, String mobileNumber, String lastname, Long id, String firstname, String emailAddress,String username) {
        this.createdAt = createdAt;
        this.type = type;
        this.physicalAddress = physicalAddress;
        this.passwordHash = passwordHash;
        this.mobileNumber = mobileNumber;
        this.lastname = lastname;
        this.id = id;
        this.firstname = firstname;
        this.emailAddress = emailAddress;
        this.username = username;
    }


}

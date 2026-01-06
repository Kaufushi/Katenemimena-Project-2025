package com.gr.hua.dit.project2025.StreetFoodGo.core.model;

import java.time.Instant;
import jakarta.persistence.*;


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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

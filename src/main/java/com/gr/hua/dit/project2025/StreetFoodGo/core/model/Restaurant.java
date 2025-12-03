package com.gr.hua.dit.project2025.StreetFoodGo.core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String area;
    private String cuisine;
    private String imageUrl;
    private Long telephone;
    private String email;

    public Restaurant() {}

    public Restaurant(String name, String area, String cuisine, String imageUrl, Long telephone, String email) {
        this.name = name;
        this.area = area;
        this.cuisine = cuisine;
        this.imageUrl = imageUrl;
        this.telephone = telephone;
        this.email = email;
    }

    // Getters / setters


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getArea() { return area; }
    public String getCuisine() { return cuisine; }
    public String getImageUrl() { return imageUrl; }
    public Long getTelephone() {
        return telephone;
    }
    public String getEmail() { return email; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setArea(String area) { this.area = area; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setTelephone(Long telephone) { this.telephone = telephone; }
    public void setEmail(String email) { this.email = email; }
}

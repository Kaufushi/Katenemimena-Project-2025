package com.gr.hua.dit.project2025.StreetFoodGo.core.service.model;


import lombok.Getter;

@Getter
public class RestaurantDto {

    private Long id;
    private String name;
    private String area;
    private String cuisine;
    private String imageUrl;

    public RestaurantDto(Long id, String name, String area, String cuisine, String imageUrl) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.cuisine = cuisine;
        this.imageUrl = imageUrl;
    }


}
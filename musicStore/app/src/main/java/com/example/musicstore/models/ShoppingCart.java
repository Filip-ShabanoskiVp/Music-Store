package com.example.musicstore.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "shoppingCarts")
public class ShoppingCart {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    String productName;

    private String description;

    private String date;

    private String image;

    private float price;

    private String username;

    public ShoppingCart(String productName, String description, String date, String image, float price, String username) {
        this.productName = productName;
        this.description = description;
        this.date = date;
        this.image = image;
        this.price = price;
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

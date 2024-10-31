package com.intelligent.ecommerce.model;

import com.google.cloud.firestore.annotation.DocumentId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Product {

    private int id;
    private String name;
    private String description;
    private double price;
    private int sales;
    private int stock;
    private String category;
    private double rating;

    // Constructors
    public Product() {}

    public Product(int id, String name, String description, double price, int sales, int stock, String category, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        this.category = category;
        this.rating = rating;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getRating() { return rating; }

    public void setRating(double rating) { this.rating = rating; }
}

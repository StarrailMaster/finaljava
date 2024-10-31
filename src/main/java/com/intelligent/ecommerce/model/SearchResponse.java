package com.intelligent.ecommerce.model;

public class SearchResponse {
    private Product product;
    private boolean clicked = false;

    // Constructor, Getters, and Setters


    public SearchResponse(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}

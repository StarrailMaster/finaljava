package com.intelligent.ecommerce.model;

import java.util.List;

public class RecommendationResponse {
    private List<Product> recommendedProducts;

    // Getter and Setter
    public List<Product> getRecommendedProducts() {
        return recommendedProducts;
    }

    public void setRecommendedProducts(List<Product> recommendedProducts) {
        this.recommendedProducts = recommendedProducts;
    }
}

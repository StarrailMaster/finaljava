package com.intelligent.ecommerce.model;

public class CustomerRecommendResponse {
    public String recommendContent;

    public CustomerRecommendResponse(String recommendContent) {
        this.recommendContent = recommendContent;
    }

    public String getRecommendContent() {
        return recommendContent;
    }

    public void setRecommendContent(String recommendContent) {
        this.recommendContent = recommendContent;
    }
}

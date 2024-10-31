package com.intelligent.ecommerce.model;

public class MerchantRecommendResponse {
    public String recommendContent;

    public MerchantRecommendResponse(String recommendContent) {
        this.recommendContent = recommendContent;
    }

    public String getRecommendContent() {
        return recommendContent;
    }

    public void setRecommendContent(String recommendContent) {
        this.recommendContent = recommendContent;
    }
}

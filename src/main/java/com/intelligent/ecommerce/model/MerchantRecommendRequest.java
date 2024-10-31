package com.intelligent.ecommerce.model;

public class MerchantRecommendRequest {

    private String searchText;
    public MerchantRecommendRequest() {
    }

    public MerchantRecommendRequest(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}


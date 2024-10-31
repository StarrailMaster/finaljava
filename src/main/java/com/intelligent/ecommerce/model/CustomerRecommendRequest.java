package com.intelligent.ecommerce.model;

public class CustomerRecommendRequest {
    private String searchText;

    public CustomerRecommendRequest() {
    }
    public CustomerRecommendRequest(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}

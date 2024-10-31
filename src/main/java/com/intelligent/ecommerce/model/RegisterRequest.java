package com.intelligent.ecommerce.model;

public class RegisterRequest {
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private String username;
    private String password;

    private int userId;
}

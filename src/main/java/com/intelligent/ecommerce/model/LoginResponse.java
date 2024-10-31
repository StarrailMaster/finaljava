package com.intelligent.ecommerce.model;

public class LoginResponse {
    private String message;
    private int userId = 0;  // 假设你希望返回用户的 ID

    // 构造函数
    public LoginResponse(String message, int userId) {
        this.message = message;
        this.userId = userId;
    }

    // Getters 和 Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

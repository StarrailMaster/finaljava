package com.intelligent.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
public class UserBehavior {
    @Id
    private int userId;

    @Id
    private int productId;

    private String behaviorType; // e.g., "click", "purchase", "view", "nonClick"
    private String timestamp; // 将 timestamp 改为 String 类型

    // 将 String 类型的 timestamp 转换为 LocalDateTime
    public LocalDateTime getLocalDateTime() {
        if (timestamp != null) {
            try {
                Instant instant = Instant.parse(timestamp);
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()); // 使用系统默认时区
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                return null; // 解析失败时返回 null 或者可以返回一个默认值
            }
        }
        return null; // 或者返回一个默认的 LocalDateTime
    }

    // Getters and Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

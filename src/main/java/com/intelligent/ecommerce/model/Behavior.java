package com.intelligent.ecommerce.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import jakarta.persistence.Entity;

@Entity
public class Behavior {

    @PropertyName("userId")
    private int userId;

    @PropertyName("productId")
    private int productId;

    @PropertyName("behaviorType")
    private String behaviorType;

    @ServerTimestamp
    @PropertyName("timestamp")
    private Timestamp timestamp;

    public Behavior(int userId, int productId, String behaviorType, Timestamp timestamp) {
        this.userId = userId;
        this.productId = productId;
        this.behaviorType = behaviorType;
        this.timestamp = timestamp;
    }

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

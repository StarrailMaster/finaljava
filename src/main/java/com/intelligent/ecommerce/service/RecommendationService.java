package com.intelligent.ecommerce.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.intelligent.ecommerce.model.Product;
import com.intelligent.ecommerce.model.UserBehavior;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class RecommendationService {

    @Autowired
    private Firestore firestore; // 注入 Firestore 实例

    // 从 Firebase 获取用户行为
    public List<UserBehavior> getUserBehaviors(int userId) {
        List<UserBehavior> userBehaviors = new ArrayList<>();
        try {
            CollectionReference userBehaviorsCollection = firestore.collection("user_behaviors");
            ApiFuture<QuerySnapshot> query = userBehaviorsCollection.whereEqualTo("userId", userId).get();
            for (QueryDocumentSnapshot document : query.get().getDocuments()) {
//                UserBehavior behavior = document.toObject(UserBehavior.class);
                UserBehavior userBehavior = new UserBehavior();
                userBehavior.setUserId(document.getLong("userId").intValue());
                userBehavior.setProductId(document.getLong("productId").intValue());
                userBehavior.setBehaviorType(document.getString("behaviorType"));

                // 获取并转换 Timestamp 为 String
                Timestamp timestamp = document.getTimestamp("timestamp");
                if (timestamp != null) {
                    userBehavior.setTimestamp(timestamp.toDate().toInstant().toString());
                }
                userBehaviors.add(userBehavior);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // 处理异常
        }
        return userBehaviors;
    }

    // 从 Firebase 获取所有产品
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            CollectionReference productsCollection = firestore.collection("Products");
            ApiFuture<QuerySnapshot> query = productsCollection.get();
            for (QueryDocumentSnapshot document : query.get().getDocuments()) {
                Product product = document.toObject(Product.class);
                products.add(product);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // 处理异常
        }
        return products;
    }

    // 计算用户对商品的推荐度
    public double calculateRecommendationScore(int userId, int productId) {
        List<UserBehavior> behaviors = getUserBehaviors(userId);
        int clicks = (int) behaviors.stream()
                .filter(b -> "click".equals(b.getBehaviorType()))
                .filter(b -> b.getProductId() == productId)
                .count();
        int nonClicks = (int) behaviors.stream()
                .filter(b -> "nonClick".equals(b.getBehaviorType()))
                .filter(b -> b.getProductId() == productId)
                .count();
        double timeDecay = calculateTimeDecay(behaviors);

        // 基础推荐分数计算
        double baseScore = (clicks * 0.5) - (nonClicks * 0.2) * timeDecay;

        // 调用 LLM 获取增强推荐分数
        double llmScore = getLLMEnhancedScore(userId, productId, baseScore);

        // 将基础分数和 LLM 提供的分数综合起来
        return baseScore + llmScore;
    }

    // 计算时间衰减
    private double calculateTimeDecay(List<UserBehavior> behaviors) {
        return behaviors.stream()
                .mapToDouble(b -> 1.0 / (1.0 + Duration.between(b.getLocalDateTime(), LocalDateTime.now()).toDays() / 30.0))
                .average()
                .orElse(1.0);
    }

    private double getLLMEnhancedScore(int userId, int productId, double baseScore) {
        OkHttpClient client = new OkHttpClient();
        final String apiKey = System.getenv("OPENAI_API_KEY");

        final String apiUrl = "https://api.openai.com/v1/chat/completions";

        ApiFuture<QuerySnapshot> querySnapshot = firestore.collection("products")
                .whereEqualTo("id", productId)
                .get();

        List<QueryDocumentSnapshot> documents = null;
        try {
            documents = querySnapshot.get().getDocuments();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        QueryDocumentSnapshot document = null;
        if (documents != null && !documents.isEmpty()) {
            document = documents.get(0);
        } else {
            return 0.0;
        }

        // 获取用户的行为历史
        List<UserBehavior> userBehaviors = getUserBehaviors(userId);

        // 构建用户行为历史字符串
        StringBuilder behaviorHistory = new StringBuilder();
        for (UserBehavior behavior : userBehaviors) {
            behaviorHistory.append(String.format("行为: %s, 产品ID: %d, 时间: %s; ",
                    behavior.getBehaviorType(), behavior.getProductId(), behavior.getTimestamp()));
        }

        // 创建 prompt
        String prompt = String.format(
                "用户ID %d 的行为历史是: %s\n" +
                        "针对产品ID %d，产品评分为 %f，用户的初始推荐评分为：%.2f。\n" +
                        "请根据用户的行为历史和产品特征提供增强评分。",
                userId, behaviorHistory, productId, document.getDouble("rating"), baseScore
        );

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("prompt", prompt);
        jsonObject.put("temperature", 0.7);
        jsonObject.put("max_tokens", 50);
        jsonObject.put("model", "gpt-4");

        RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JSONObject responseBody = new JSONObject(response.body().string());
                String llmResponse = responseBody.getJSONArray("choices").getJSONObject(0).getString("text").trim();
                return Double.parseDouble(llmResponse);  // 假设 LLM 返回的是数值
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0.0;  // LLM 调用失败时返回默认值
    }
}


package com.intelligent.ecommerce.controller;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.intelligent.ecommerce.model.*;
import com.google.cloud.firestore.Firestore;
import com.intelligent.ecommerce.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class SearchProductController {

    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private Firestore firestore;

    @PostMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestBody SearchRequest searchRequest) {

        List<Product> products = new ArrayList<>();
        List<QueryDocumentSnapshot> documents = new ArrayList<>();
        List<QueryDocumentSnapshot> matchingDocuments = new ArrayList<>();
        String regexPattern = ".*" + Pattern.quote(searchRequest.getKeyword()) + ".*"; // 构建正则表达式
        Pattern pattern = Pattern.compile(regexPattern);

        try {
            // Search by product category and sort in descending order of sales volume
            documents = firestore.collection("Products")
                    .get()
                    .get()
                    .getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                String name = document.getString("name");
                String category = document.getString("category");
                String description = document.getString("description");
                if (name != null && pattern.matcher(name).find()) {
                    matchingDocuments.add(document);
                } else if (category != null && pattern.matcher(category).find()) {
                    matchingDocuments.add(document);
                } else if (description != null && pattern.matcher(description).find()) {
                    matchingDocuments.add(document);
                }
            }

            for (QueryDocumentSnapshot document : matchingDocuments) {
                Product product = document.toObject(Product.class);
                products.add(product);
            }

            // If no matching product is found, return a prompt message
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body("Please enter the correct keyword; Or use AI tools to help you generate product names.");
            }

            List<UserBehavior> userBehaviors = recommendationService.getUserBehaviors(searchRequest.getUserId()); // 假设有这个方法来获取用户行为数据
            if (!userBehaviors.isEmpty()) {
                products.sort((p1, p2) -> Double.compare(
                        recommendationService.calculateRecommendationScore(searchRequest.getUserId(), p2.getId()),
                        recommendationService.calculateRecommendationScore(searchRequest.getUserId(), p1.getId())
                ));
            }

            // Traverse the results and encapsulate the data into SearchResponse
            List<SearchResponse> responses = products.stream()
                    .map(SearchResponse::new)  // 使用 SearchResponse 的构造函数将 Product 转换为 SearchResponse
                    .toList();  // 收集结果为 List<SearchResponse>

            return ResponseEntity.ok(responses);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while searching for products.");
        }

    }
}
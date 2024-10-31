package com.intelligent.ecommerce.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.intelligent.ecommerce.model.Product;
import com.intelligent.ecommerce.model.RecommendationRequest;
import com.intelligent.ecommerce.model.RecommendationResponse;
import com.intelligent.ecommerce.model.UserBehavior;
import com.intelligent.ecommerce.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @PostMapping("/user")
    public RecommendationResponse recommendForUser(@RequestBody RecommendationRequest request) {
        List<UserBehavior> userBehaviors = recommendationService.getUserBehaviors(request.getUserId()); // 假设有这个方法来获取用户行为数据
        List<Product> products = recommendationService.getAllProducts();
        if (userBehaviors.isEmpty()) {

            RecommendationResponse response = new RecommendationResponse();
            response.setRecommendedProducts(products);
            return response;

        } else {

            products.sort((p1, p2) -> Double.compare(
                    recommendationService.calculateRecommendationScore(request.getUserId(), p2.getId()),
                    recommendationService.calculateRecommendationScore(request.getUserId(), p1.getId())
            ));

            RecommendationResponse response = new RecommendationResponse();
            response.setRecommendedProducts(products);
            return response;

        }
    }
}


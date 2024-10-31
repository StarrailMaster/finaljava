package com.intelligent.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.intelligent.ecommerce.model.MerchantRecommendRequest;
import com.intelligent.ecommerce.model.MerchantRecommendResponse;
import com.intelligent.ecommerce.service.MerchantRecommendService;

@RestController
@RequestMapping("/api/merchant_recommend")
public class MerchantRecommendController {

    private final MerchantRecommendService merchantRecommendService;

    public MerchantRecommendController(MerchantRecommendService merchantRecommendService) {
        this.merchantRecommendService = merchantRecommendService;
    }

    @PostMapping("/generateRecommend")
    public MerchantRecommendResponse generateMerchantRecommend(@RequestBody MerchantRecommendRequest request) {
        System.out.println("recommend: CONNECTED");

        String searchText = request.getSearchText();

        String recommendContent = merchantRecommendService.generateMerchantRecommend(searchText);
        System.out.println("recommend: " + recommendContent);

        return new MerchantRecommendResponse(recommendContent);
    }
}
package com.intelligent.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.intelligent.ecommerce.model.CustomerRecommendRequest;
import com.intelligent.ecommerce.model.CustomerRecommendResponse;
import com.intelligent.ecommerce.service.CustomerRecommendService;

@RestController
@RequestMapping("/api/customer_recommend")
public class CustomerRecommendController {

    private final CustomerRecommendService customerRecommendService;

    public CustomerRecommendController(CustomerRecommendService customerRecommendService) {
        this.customerRecommendService = customerRecommendService;
    }

    @PostMapping("/generateRecommend")
    public CustomerRecommendResponse generateCustomerRecommend(@RequestBody CustomerRecommendRequest request) {
        System.out.println("recommend: CONNECTED");

        String searchText = request.getSearchText();

        String recommendContent = customerRecommendService.generateCustomerRecommend(searchText);
        System.out.println("recommend: " + recommendContent);

        return new CustomerRecommendResponse(recommendContent);
    }
}


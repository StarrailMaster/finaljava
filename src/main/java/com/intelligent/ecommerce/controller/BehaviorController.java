package com.intelligent.ecommerce.controller;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.intelligent.ecommerce.model.Behavior;
import com.intelligent.ecommerce.model.UserBehavior;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Date;


@RestController
@RequestMapping("/api/behavior")
public class BehaviorController {

    @PostMapping("/log")
    public String logBehavior(@RequestBody List<UserBehavior> userBehaviors) {
        Firestore db = FirestoreClient.getFirestore();
        for (UserBehavior behavior : userBehaviors){
            Behavior bh = new Behavior(
                    behavior.getUserId(),
                    behavior.getProductId(),
                    behavior.getBehaviorType(),
                    Timestamp.of(Date.from(Instant.parse(behavior.getTimestamp())))
            );
            db.collection("user_behaviors").add(bh);
        }
        return "Behavior logged successfully";
    }
}
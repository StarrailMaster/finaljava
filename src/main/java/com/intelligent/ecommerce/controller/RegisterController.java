package com.intelligent.ecommerce.controller;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.intelligent.ecommerce.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private Firestore firestore;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        Random random = new Random();
        userInfo.setUserId(random.nextInt(100000));


        // 检查用户是否已存在
        try {
            QuerySnapshot querySnapshot = firestore.collection("Users").whereEqualTo("username", username).get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            if (!documents.isEmpty()) {
                return ResponseEntity.status(409).body("User already exists");
            }

            // 将新用户添加到Firestore
            firestore.collection("Users").add(userInfo);

            return ResponseEntity.ok("Successful registration");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server Error");
        }
    }
}

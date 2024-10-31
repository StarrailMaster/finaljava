package com.intelligent.ecommerce.controller;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.intelligent.ecommerce.model.LoginRequest;
import com.intelligent.ecommerce.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private Firestore firestore;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();

        // Firestore查找比对
        try {
            QuerySnapshot querySnapshot = firestore.collection("Users").whereEqualTo("username", username).get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            if (!documents.isEmpty()) {
                String storedPassword = documents.get(0).getString("password");
                int userId = documents.get(0).getLong("userId").intValue();

                if (storedPassword != null && storedPassword.equals(password)) {
                    // 登录成功，返回 LoginResponse 对象
                    LoginResponse loginResponse = new LoginResponse("Login successful", userId);
                    return ResponseEntity.ok(loginResponse);
                } else {
                    return ResponseEntity.status(401).body(new LoginResponse("Wrong password", 0));
                }
            } else {
                return ResponseEntity.status(404).body(new LoginResponse("User not found", 0));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new LoginResponse("Server Error", 0));
        }
    }
}


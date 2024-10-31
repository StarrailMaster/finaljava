package com.intelligent.ecommerce.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


@Configuration
public class FirebaseConfig {

    @Bean
    public Firestore getFirestore() {
        try {
            // 使用类路径加载 JSON 文件
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("intelligentecommerce-758d8-firebase-adminsdk-elhud-3797925567.json");

            if (serviceAccount == null) {
                throw new FileNotFoundException("Firebase service account file not found in resources.");
            }

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://intelligentecommerce-758d8.firebaseio.com")
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

            return FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to initialize Firebase", e);
        }
    }
}

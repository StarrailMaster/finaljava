package com.intelligent.ecommerce.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class MerchantRecommendService {

    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey = System.getenv("OPENAI_API_KEY");

    private final String apiUrl = "https://api.openai.com/v1/chat/completions";

    public String generateMerchantRecommend(String SearchText) {
        if (apiKey == null) {
            throw new IllegalArgumentException("API key not found");
        }

        String prompt = String.format(
                "You are an AI customer support assistant for an intelligent e-commerce platform.\n" +
                        "Based on the merchant's inquiry below, provide relevant product recommendations and insights:\n\n" +
                        "Merchant Inquiry: \"%s\"\n\n" +
                        "Recommendations:\n" +
                        "1. [Recommendation 1]\n" +
                        "2. [Recommendation 2]\n" +
                        "3. [Recommendation 3]\n" +
                        "Please provide a brief description of each recommendation and how it can help the merchant improve their sales or business strategy.",
                JSONObject.quote(SearchText)
        );

        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("model", "gpt-4");
        JSONArray messagesArray = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", prompt);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", SearchText);

        messagesArray.put(systemMessage);
        messagesArray.put(userMessage);

        requestBodyJson.put("messages", messagesArray);

        RequestBody requestBody = RequestBody.create(
                requestBodyJson.toString(),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return parseConsultLetterResponse(response.body().string());
            } else {
                return "Failed to generate merchant recommend.";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String parseConsultLetterResponse(String response) {
        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                return jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            } catch (Exception e) {
                return "Error parsing response: " + e.getMessage();
            }
        }
        return "Failed to generate merchant recommend.";
    }
}

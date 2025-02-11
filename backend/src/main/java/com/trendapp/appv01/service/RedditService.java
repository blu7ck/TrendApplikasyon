package com.trendapp.appv01.service;

import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.nio.charset.StandardCharsets;

@Service
public class RedditService {

    @Value("${reddit.client.id}")
    private String clientId;

    @Value("${reddit.client.secret}")
    private String clientSecret;

    @Value("${reddit.username}")
    private String username;

    @Value("${reddit.password}")
    private String password;

    @Value("${reddit.useragent}")
    private String userAgent;

    private String accessToken;

    @PostConstruct
    public void init() {
        authenticate();
    }

    public void authenticate() {
        try {
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Reddit password is missing! Check application.properties or environment variables.");
            }

            HttpClient client = HttpClient.newHttpClient();
            String encodedPassword = URLEncoder.encode(password, StandardCharsets.UTF_8);
            String auth = "grant_type=password&username=" + username + "&password=" + encodedPassword;
            String encodedCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.reddit.com/api/v1/access_token"))
                    .header("Authorization", "Basic " + encodedCredentials)
                    .header("User-Agent", userAgent)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(auth))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            if (json.has("access_token")) {
                accessToken = json.getString("access_token");
                System.out.println("✅ Reddit Access Token Alındı: " + accessToken);
            } else {
                System.out.println("⚠️ Hata: API yanıtında 'access_token' bulunamadı!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAccessToken() {
        if (accessToken == null) {
            authenticate();
        }
        return accessToken;
    }

    public List<Map<String, String>> fetchTopPosts() {
        List<Map<String, String>> postsList = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://oauth.reddit.com/r/popular/top.json?limit=10"))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("User-Agent", userAgent)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray posts = jsonResponse.getJSONObject("data").getJSONArray("children");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.getJSONObject(i).getJSONObject("data");

                Map<String, String> postDetails = new HashMap<>();
                postDetails.put("title", post.getString("title"));
                postDetails.put("url", post.getString("url"));
                postDetails.put("thumbnail", post.has("thumbnail") ? post.getString("thumbnail") : null);

                postsList.add(postDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postsList;
    }
}

package com.trendapp.appv01.reddit.service;

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
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;

@Service
public class RedditService {

    @Value("${reddit.client.id}")
    private String clientId;

    @Value("${reddit.client.secret}")
    private String clientSecret;

    @Value("${reddit.username}")
    private String username;

    @Value("${reddit.password:DEFAULT_PASSWORD}")
    private String password;


    @Value("${reddit.useragent}")
    private String userAgent;

    private String accessToken; // Access token değişkeni eklendi

    @PostConstruct
    public void init() {
        authenticate(); // ✅ Spring değerleri enjekte ettikten sonra çalıştır
    }


    @PostConstruct
    public void checkConfig() {
        System.out.println("🔍 Reddit Config - Username: " + username);
        System.out.println("🔍 Reddit Config - Password: " + (password == null ? "NULL" : "LOADED"));
    }

    // 🔥 TOKEN ALMA METODU
    public void authenticate() {
        try {
            System.out.println("🔍 Reddit Config - Username: " + username);
            System.out.println("🔍 Reddit Config - Password: " + password); // DEBUG LOG

            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Reddit password is missing! Check application.properties or environment variables.");
            }

            HttpClient client = HttpClient.newHttpClient();
            String encodedPassword = URLEncoder.encode(password, "UTF-8");
            String auth = "grant_type=password&username=" + username + "&password=" + encodedPassword;
            String encodedCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.reddit.com/api/v1/access_token"))
                    .header("Authorization", "Basic " + encodedCredentials)
                    .header("User-Agent", userAgent)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(auth))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("🔍 Reddit Auth Response: " + response.body()); // API CEVABINI GÖRMEK İÇİN

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


    // **TOKEN GETİRME METODU EKLENDİ** ✅
    public String getAccessToken() {
        if (accessToken == null) {
            authenticate(); // Eğer token yoksa, tekrar al
        }
        return accessToken;
    }

    // 🔥 POPÜLER POSTLARI ÇEKEN METOT
    public List<String> fetchTopPosts() { // ✅ Parametresiz hale getirdik
        List<String> postLinks = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://oauth.reddit.com/r/popular/top.json?limit=10"))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("User-Agent", "TrendApp/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray posts = jsonResponse.getJSONObject("data").getJSONArray("children");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.getJSONObject(i).getJSONObject("data");
                String title = post.getString("title");
                String url = post.getString("url");
                postLinks.add(title + " - " + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postLinks; // ✅ Artık gerçekten bir liste döndürüyor
    }
}
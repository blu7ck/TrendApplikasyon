package com.trendapp.appv01.x.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterService {

    @Value("${twitter.bearer.token}")
    private String bearerToken;

    private static final String TWITTER_TRENDS_URL = "https://api.twitter.com/1.1/trends/place.json?id=23424969"; // Türkiye ID'si

    public List<String> fetchTrendingTweets() { // ✅ DÖNÜŞ TİPİ `void` DEĞİL, `List<String>` OLDU
        List<String> trends = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TWITTER_TRENDS_URL))
                    .header("Authorization", "Bearer " + bearerToken)
                    .header("User-Agent", "TrendApp/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Twitter Trends Response: " + response.body());

            JSONArray jsonArray = new JSONArray(response.body());
            JSONObject trendsObject = jsonArray.getJSONObject(0);
            JSONArray trendsArray = trendsObject.getJSONArray("trends");

            for (int i = 0; i < Math.min(10, trendsArray.length()); i++) {
                JSONObject trend = trendsArray.getJSONObject(i);
                String name = trend.getString("name");
                String url = trend.getString("url");
                trends.add(name + " - " + url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return trends; // ✅ ŞİMDİ GERÇEKTEN VERİ DÖNDÜRÜYOR
    }
}

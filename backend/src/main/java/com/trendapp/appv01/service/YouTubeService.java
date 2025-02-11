package com.trendapp.appv01.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private static final String YOUTUBE_TRENDING_URL = "https://www.googleapis.com/youtube/v3/videos?"
            + "part=snippet,statistics"
            + "&chart=mostPopular"
            + "&regionCode=TR"
            + "&maxResults=10"
            + "&key=";

    public List<Map<String, Object>> fetchTrendingVideos() {
        List<Map<String, Object>> videos = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(YOUTUBE_TRENDING_URL + apiKey))
                    .header("User-Agent", "TrendApp/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // API'den dönen JSON verisini terminale yazdır
            System.out.println("YouTube API Response: " + response.body());

            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.has("items")) {
                JSONArray items = jsonResponse.getJSONArray("items");

                for (int i = 0; i < items.length(); i++) {
                    JSONObject video = items.getJSONObject(i);
                    JSONObject snippet = video.getJSONObject("snippet");
                    JSONObject statistics = video.has("statistics") ? video.getJSONObject("statistics") : new JSONObject();

                    Map<String, Object> videoData = new HashMap<>();
                    videoData.put("title", snippet.getString("title"));
                    videoData.put("description", snippet.getString("description"));
                    videoData.put("videoId", video.getString("id"));
                    videoData.put("url", "https://www.youtube.com/watch?v=" + video.getString("id"));
                    videoData.put("views", statistics.optInt("viewCount", 0)); // Bazı videolarda viewCount olmayabilir

                    if (snippet.has("thumbnails")) {
                        videoData.put("thumbnail", snippet.getJSONObject("thumbnails").getJSONObject("medium").getString("url"));
                    } else {
                        videoData.put("thumbnail", ""); // Varsayılan boş görsel
                    }

                    videos.add(videoData);
                }
            } else {
                System.err.println("HATA: API 'items' anahtarını içermiyor!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }
}

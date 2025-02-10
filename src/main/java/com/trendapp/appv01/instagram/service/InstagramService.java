package com.trendapp.appv01.instagram.service;

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
public class InstagramService {

    @Value("${instagram.api.token}")
    private String accessToken;

    @Value("${instagram.user.id}")
    private String instagramUserId;

    private static final String INSTAGRAM_API_URL = "https://graph.facebook.com/v22.0";

    public List<String> fetchTopPosts() {
        List<String> trendingPosts = new ArrayList<>();
        try {
            String hashtag = "trending"; // Varsayılan hashtag
            String hashtagId = getHashtagId(hashtag);
            if (hashtagId == null) {
                return List.of("Hashtag bulunamadı!");
            }

            String url = INSTAGRAM_API_URL + "/" + hashtagId + "/top_media?user_id=" + instagramUserId +
                    "&fields=id,caption,media_type,media_url,permalink&access_token=" + accessToken;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "TrendApp/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());

            if (jsonResponse.has("data")) {
                JSONArray posts = jsonResponse.getJSONArray("data");

                for (int i = 0; i < Math.min(10, posts.length()); i++) {
                    JSONObject post = posts.getJSONObject(i);
                    String caption = post.optString("caption", "No caption");
                    String mediaType = post.getString("media_type");
                    String mediaUrl = post.getString("media_url");
                    String permalink = post.getString("permalink");

                    String formattedPost = String.format("Type: %s\n%s\nURL: %s\nPermalink: %s",
                            mediaType, caption, mediaUrl, permalink);

                    trendingPosts.add(formattedPost);
                }
            } else {
                System.err.println("HATA: API 'data' anahtarını içermiyor!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return List.of("Hata oluştu: " + e.getMessage());
        }
        return trendingPosts;
    }


    private String getHashtagId(String hashtag) {
        try {
            String url = INSTAGRAM_API_URL + "/ig_hashtag_search?user_id=" + instagramUserId +
                    "&q=" + hashtag + "&access_token=" + accessToken;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "TrendApp/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());

            if (jsonResponse.has("data")) {
                JSONArray hashtags = jsonResponse.getJSONArray("data");
                if (hashtags.length() > 0) {
                    return hashtags.getJSONObject(0).getString("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

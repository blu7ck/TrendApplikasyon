package com.trendapp.appv01.controller;

import com.trendapp.appv01.reddit.service.RedditService;
import com.trendapp.appv01.x.service.TwitterService;
import com.trendapp.appv01.youtube.service.YouTubeService;
import com.trendapp.appv01.instagram.service.InstagramService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UnifiedController {

    private final RedditService redditService;
    private final TwitterService twitterService;
    private final YouTubeService youtubeService;
    private final InstagramService instagramService;

    public UnifiedController(RedditService redditService, TwitterService twitterService,
                             YouTubeService youtubeService, InstagramService instagramService) {
        this.redditService = redditService;
        this.twitterService = twitterService;
        this.youtubeService = youtubeService;
        this.instagramService = instagramService;
    }

    @GetMapping("/trending")
    public Map<String, Object> getTrendingContent(@RequestParam(defaultValue = "trend") String hashtag) {
        Map<String, Object> response = new HashMap<>();
        response.put("lastUpdated", LocalDateTime.now().toString()); // Güncellenme zamanı

        response.put("reddit", redditService.fetchTopPosts());
        response.put("twitter", twitterService.fetchTrendingTweets());
        response.put("youtube", youtubeService.fetchTrendingVideos());
        response.put("instagram", instagramService.fetchTopPosts());

        return response;
    }
}

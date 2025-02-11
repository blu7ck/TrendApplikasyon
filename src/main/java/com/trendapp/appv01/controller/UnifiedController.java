package com.trendapp.appv01.controller;

import com.trendapp.appv01.reddit.service.RedditService;

import com.trendapp.appv01.youtube.service.YouTubeService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UnifiedController {

    private final RedditService redditService;

    private final YouTubeService youtubeService;


    public UnifiedController(RedditService redditService,
                             YouTubeService youtubeService) {
        this.redditService = redditService;

        this.youtubeService = youtubeService;

    }

    @GetMapping("/trending")
    public Map<String, Object> getTrendingContent(@RequestParam(defaultValue = "trend") String hashtag) {
        Map<String, Object> response = new HashMap<>();
        response.put("lastUpdated", LocalDateTime.now().toString()); // Güncellenme zamanı

        response.put("reddit", redditService.fetchTopPosts());

        response.put("youtube", youtubeService.fetchTrendingVideos());


        return response;
    }
}

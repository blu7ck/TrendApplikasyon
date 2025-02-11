package com.trendapp.appv01.controller;

import com.trendapp.appv01.service.RedditService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reddit")
public class RedditController {

    private final RedditService redditService;

    public RedditController(RedditService redditService) {
        this.redditService = redditService;
    }

    @GetMapping("/top")
    public List<Map<String, String>> fetchTopPosts() {
        return redditService.fetchTopPosts();
    }

    @GetMapping("/token")
    public String getAccessToken() {
        return redditService.getAccessToken();
    }
}

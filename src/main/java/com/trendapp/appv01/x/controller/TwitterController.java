package com.trendapp.appv01.x.controller;

import com.trendapp.appv01.x.service.TwitterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/twitter")
public class TwitterController {

    private final TwitterService twitterService;

    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/top")
    public List<String> getTrendingTweets() {
        return twitterService.fetchTrendingTweets(); // ✅ HATA BURADA OLUYORDU, ŞİMDİ ÇÖZÜLDÜ
    }
}

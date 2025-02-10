package com.trendapp.appv01.instagram.controller;

import com.trendapp.appv01.instagram.service.InstagramService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/instagram")
public class InstagramController {

    private final InstagramService instagramService;

    public InstagramController(InstagramService instagramService) {
        this.instagramService = instagramService;
    }

    @GetMapping("/top")
    public List<String> getTrendingInstagramPosts(@RequestParam String hashtag) {
        return instagramService.fetchTopPosts();
    }
}

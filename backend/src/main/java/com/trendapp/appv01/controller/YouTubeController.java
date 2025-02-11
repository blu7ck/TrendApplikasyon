package com.trendapp.appv01.controller;

import com.trendapp.appv01.service.YouTubeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/youtube")
@CrossOrigin(origins = "http://localhost:3000")
public class YouTubeController {

    private final YouTubeService youtubeService;

    public YouTubeController(YouTubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping(value = "/trends", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getTrendingVideos() {
        List<Map<String, Object>> videos = youtubeService.fetchTrendingVideos();

        if (videos == null || videos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }

        return ResponseEntity.ok().body(videos);
    }
}

package com.trendapp.appv01.service;

import com.trendapp.appv01.service.RedditService;


import com.trendapp.appv01.service.YouTubeService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    private final RedditService redditService;

    private final YouTubeService youtubeService;


    public SchedulerService(RedditService redditService,
                            YouTubeService youtubeService) {
        this.redditService = redditService;

        this.youtubeService = youtubeService;

    }

    @Scheduled(cron = "0 0 6 * * ?")  // Sabah 06:00
    public void fetchNightActivity() {
        System.out.println("🔄 [06:00] Gece aktiviteleri güncelleniyor...");
        redditService.fetchTopPosts();

        youtubeService.fetchTrendingVideos();

    }

    @Scheduled(cron = "0 0 12 * * ?")  // Öğlen 12:00
    public void fetchMorningUpdates() {
        System.out.println("🔄 [12:00] Sabah gelişmeleri güncelleniyor...");
        redditService.fetchTopPosts();

        youtubeService.fetchTrendingVideos();

    }

    @Scheduled(cron = "0 0 17 * * ?")  // Akşam 17:00
    public void fetchAfternoonUpdates() {
        System.out.println("🔄 [17:00] Öğlen gelişmeleri güncelleniyor...");
        redditService.fetchTopPosts();

        youtubeService.fetchTrendingVideos();

    }

    @Scheduled(cron = "0 0 21 * * *") // Akşam 21:00'de çalışacak
    public void fetchDailySummary() {
        System.out.println("🕘 [21:00] Günün en popüler içerikleri güncelleniyor...");

        System.out.println("📥 Reddit: " + redditService.fetchTopPosts());

        System.out.println("📥 YouTube: " + youtubeService.fetchTrendingVideos());

    }
}

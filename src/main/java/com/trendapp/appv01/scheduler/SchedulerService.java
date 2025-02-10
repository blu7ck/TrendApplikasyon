package com.trendapp.appv01.scheduler;

import com.trendapp.appv01.reddit.service.RedditService;

import com.trendapp.appv01.x.service.TwitterService;
import com.trendapp.appv01.youtube.service.YouTubeService;
import com.trendapp.appv01.instagram.service.InstagramService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    private final RedditService redditService;
    private final TwitterService twitterService;
    private final YouTubeService youtubeService;
    private final InstagramService instagramService;

    public SchedulerService(RedditService redditService, TwitterService twitterService,
                            YouTubeService youtubeService, InstagramService instagramService) {
        this.redditService = redditService;
        this.twitterService = twitterService;
        this.youtubeService = youtubeService;
        this.instagramService = instagramService;
    }

    @Scheduled(cron = "0 0 6 * * ?")  // Sabah 06:00
    public void fetchNightActivity() {
        System.out.println("🔄 [06:00] Gece aktiviteleri güncelleniyor...");
        redditService.fetchTopPosts();
        twitterService.fetchTrendingTweets();
        youtubeService.fetchTrendingVideos();
        instagramService.fetchTopPosts();
    }

    @Scheduled(cron = "0 0 12 * * ?")  // Öğlen 12:00
    public void fetchMorningUpdates() {
        System.out.println("🔄 [12:00] Sabah gelişmeleri güncelleniyor...");
        redditService.fetchTopPosts();
        twitterService.fetchTrendingTweets();
        youtubeService.fetchTrendingVideos();
        instagramService.fetchTopPosts();
    }

    @Scheduled(cron = "0 0 17 * * ?")  // Akşam 17:00
    public void fetchAfternoonUpdates() {
        System.out.println("🔄 [17:00] Öğlen gelişmeleri güncelleniyor...");
        redditService.fetchTopPosts();
        twitterService.fetchTrendingTweets();
        youtubeService.fetchTrendingVideos();
        instagramService.fetchTopPosts();
    }

    @Scheduled(cron = "0 0 21 * * *") // Akşam 21:00'de çalışacak
    public void fetchDailySummary() {
        System.out.println("🕘 [21:00] Günün en popüler içerikleri güncelleniyor...");

        System.out.println("📥 Reddit: " + redditService.fetchTopPosts());
        System.out.println("📥 Twitter: " + twitterService.fetchTrendingTweets());
        System.out.println("📥 YouTube: " + youtubeService.fetchTrendingVideos());
        System.out.println("📥 Instagram: " + instagramService.fetchTopPosts()); // 🔥 Instagram için düzeltildi
    }
}

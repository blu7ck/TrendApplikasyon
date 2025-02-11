const API_URL = process.env.REACT_APP_API_URL;

export const fetchRedditData = async () => {
    try {
        const response = await fetch(process.env.REACT_APP_REDDIT_API_URL);
        if (!response.ok) throw new Error("Reddit API isteği başarısız oldu.");
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Reddit verisi çekilirken hata oluştu:", error);
        return [];
    }
};

export const fetchYouTubeData = async () => {
    try {
        const response = await fetch(process.env.REACT_APP_YOUTUBE_API_URL);
        if (!response.ok) throw new Error("YouTube API isteği başarısız oldu.");
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("YouTube verisi çekilirken hata oluştu:", error);
        return [];
    }
};

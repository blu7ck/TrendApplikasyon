export const fetchRedditData = async () => {
    const response = await fetch("https://www.reddit.com/r/popular/top.json?limit=10");
    const data = await response.json();
    return data.data.children.map(post => ({
        title: post.data.title,
        url: post.data.url,
        thumbnail: post.data.thumbnail !== "self" ? post.data.thumbnail : null
    }));
};


export const fetchTwitterData = async () => {
    const response = await fetch("API_URL_TWITTER");
    const data = await response.json();
    return data.map(tweet => ({
        title: tweet.text,
        url: `https://twitter.com/user/status/${tweet.id}`,
        thumbnail: tweet.media ? tweet.media[0].url : null
    }));
};

export const fetchYouTubeData = async () => {
    try {
        console.log("YouTube verisi çekiliyor...");

        const response = await fetch("http://localhost:8080/api/youtube/trends");

        // 1️⃣ HTTP Yanıt Kontrolü (200 değilse hata fırlat)
        if (!response.ok) {
            throw new Error(`HTTP hata! Durum kodu: ${response.status}`);
        }

        // 2️⃣ Yanıtın JSON olup olmadığını kontrol et
        const contentType = response.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
            throw new Error("Sunucudan JSON formatında veri gelmiyor!");
        }

        // 3️⃣ JSON Parse İşlemi
        const data = await response.json();
        console.log("YouTube API Yanıtı:", data);

        // 4️⃣ Beklenen formatı doğrula
        if (!Array.isArray(data)) {
            throw new Error("Beklenmeyen veri formatı! API'den gelen veri array değil.");
        }

        return data.map(video => ({
            title: video.title,
            url: video.url,
            thumbnail: video.thumbnail || "",
        }));

    } catch (error) {
        console.error("YouTube API Hatası:", error);
        return [];
    }
};
















export const fetchInstagramData = async () => {
    const response = await fetch("API_URL_INSTAGRAM");
    const data = await response.json();
    return data.map(post => ({
        title: post.caption,
        url: post.permalink,
        thumbnail: post.media_url
    }));
};

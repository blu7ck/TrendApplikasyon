import React, { useEffect, useState } from "react";

const YouTubeFeed = () => {
    const [videos, setVideos] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                console.log("YouTube verisi çekilmeye başlandı...");
                const response = await fetch("http://localhost:8080/api/youtube/trends");

                if (!response.ok) {
                    throw new Error(`Sunucu hatası: ${response.status}`);
                }

                const contentType = response.headers.get("content-type");
                if (!contentType || !contentType.includes("application/json")) {
                    throw new Error("Gelen yanıt JSON formatında değil.");
                }

                const data = await response.json();
                console.log("Gelen YouTube Verisi:", data);

                if (Array.isArray(data)) {
                    setVideos(data);
                } else {
                    console.error("API beklenen formatta veri göndermedi.");
                    setVideos([]);
                }

            } catch (error) {
                console.error("YouTube verisi alınamadı:", error);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="platform-container youtube">
            <h2>YouTube</h2>
            {videos.length === 0 ? <p>Veri bulunamadı!</p> : null}
            <ul>
                {videos.map((video, index) => (
                    <li key={index}>
                        <a href={video.url} target="_blank" rel="noopener noreferrer">
                            <img src={video.thumbnail} alt="thumbnail" width="50" /> {video.title}
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default YouTubeFeed;

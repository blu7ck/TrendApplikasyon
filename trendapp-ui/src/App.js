import React, { useEffect, useState } from "react";
import { fetchRedditData, fetchTwitterData, fetchYouTubeData, fetchInstagramData } from "./api";
import "./App.css";

function App() {
    const [reddit, setReddit] = useState([]);
    const [twitter, setTwitter] = useState([]);
    const [youtube, setYouTube] = useState([]);
    const [instagram, setInstagram] = useState([]);
    const [updateTime, setUpdateTime] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            console.log("🔵 Veri çekme işlemi başladı...");

            const redditData = await fetchRedditData();
            console.log("✅ Reddit Verisi:", redditData);
            setReddit(redditData);

            const twitterData = await fetchTwitterData();
            console.log("✅ Twitter Verisi:", twitterData);
            setTwitter(twitterData);

            const youtubeData = await fetchYouTubeData();
            console.log("✅ YouTube Verisi:", youtubeData);
            setYouTube(youtubeData);

            const instagramData = await fetchInstagramData();
            console.log("✅ Instagram Verisi:", instagramData);
            setInstagram(instagramData);

            setUpdateTime(new Date().toLocaleTimeString());
        };

        fetchData();
        const interval = setInterval(fetchData, 1000 * 60 * 5); // 5 dakikada bir güncelle
        return () => clearInterval(interval);
    }, []);

    return (
        <div className="app-container">
            <header>
                <h1>Trend App</h1>
                <span className="update-time">Son Güncelleme: {updateTime}</span>
            </header>

            <div className="content">
                <PlatformSection title="Reddit" data={reddit} className="reddit" />
                <PlatformSection title="YouTube" data={youtube} className="youtube" />
                <PlatformSection title="Twitter" data={twitter} className="twitter" />
                <PlatformSection title="Instagram" data={instagram} className="instagram" />
            </div>
        </div>
    );
}

const PlatformSection = ({ title, data, className }) => (
    <div className={`platform-container ${className}`}>
        <h2>{title}</h2>
        <ul>
            {data.length === 0 ? (
                <p>❌ Veri bulunamadı!</p>
            ) : (
                data.map((item, index) => (
                    <li key={index}>
                        <a href={item.url} target="_blank" rel="noopener noreferrer">
                            {item.thumbnail && <img src={item.thumbnail} alt="thumbnail" width="50" />} {item.title}
                        </a>
                    </li>
                ))
            )}
        </ul>
    </div>
);

export default App;

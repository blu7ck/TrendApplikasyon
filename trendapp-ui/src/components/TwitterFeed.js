import React, { useEffect, useState } from "react";

const TwitterFeed = () => {
    const [tweets, setTweets] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/twitter") // Backend API endpoint
            .then((response) => response.json())
            .then((data) => setTweets(data))
            .catch((error) => console.error("Twitter verisi alınamadı:", error));
    }, []);

    return (
        <div className="platform-container twitter">
            <h2>Twitter</h2>
            <ul>
                {tweets.map((tweet, index) => (
                    <li key={index}>
                        <a href={tweet.url} target="_blank" rel="noopener noreferrer">
                            {tweet.text}
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TwitterFeed;

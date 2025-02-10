import React, { useEffect, useState } from "react";

const InstagramFeed = () => {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/instagram") // Backend API endpoint
            .then((response) => response.json())
            .then((data) => setPosts(data))
            .catch((error) => console.error("Instagram verisi alınamadı:", error));
    }, []);

    return (
        <div className="platform-container instagram">
            <h2>Instagram</h2>
            <ul>
                {posts.map((post, index) => (
                    <li key={index}>
                        <a href={post.url} target="_blank" rel="noopener noreferrer">
                            {post.caption}
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default InstagramFeed;

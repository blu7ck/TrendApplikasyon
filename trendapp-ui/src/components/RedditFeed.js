import React, { useEffect, useState } from "react";

const RedditFeed = () => {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/reddit") // Backend API endpoint
            .then((response) => response.json())
            .then((data) => setPosts(data))
            .catch((error) => console.error("Reddit verisi alınamadı:", error));
    }, []);

    return (
        <div className="platform-container reddit">
            <h2>Reddit</h2>
            <ul>
                {posts.map((post, index) => (
                    <li key={index}>
                        <a href={post.url} target="_blank" rel="noopener noreferrer">
                            {post.title}
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default RedditFeed;

package com.poetofcode.site2api_sample.data.model

data class FeedResponse(
    val posts: List<Post>
) {
    data class Post(
        val title: String,
        val image: String,
        val link: String,
        val commentsCount: String
    )
}

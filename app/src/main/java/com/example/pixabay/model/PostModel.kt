package com.example.pixabay.model

data class PostModel(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)
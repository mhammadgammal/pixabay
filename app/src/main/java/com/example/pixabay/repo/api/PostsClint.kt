package com.example.pixabay.repo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostsClint {
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: PostsApiInterface by lazy {
        retrofit.create(PostsApiInterface::class.java)
    }
}

const val BASE_URL = "https://pixabay.com/api/"
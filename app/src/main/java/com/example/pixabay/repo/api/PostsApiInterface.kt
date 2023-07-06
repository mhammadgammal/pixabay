package com.example.pixabay.repo.api

import com.example.pixabay.model.PostModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApiInterface {
    @GET("?key=38062418-652b7703c24f95911be66702c")
    suspend fun getPosts(): PostModel

    @GET("?key=38062418-652b7703c24f95911be66702c")
    suspend fun getPostsByTag(@Query("q") tag: String): PostModel

}

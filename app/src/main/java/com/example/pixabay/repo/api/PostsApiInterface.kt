package com.example.pixabay.repo.api

import com.example.pixabay.model.PostModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApiInterface {
    @GET("?key=26189611-305d8ed20ad78b772e9585436")
    suspend fun getPosts(): PostModel

    @GET("?key=26189611-305d8ed20ad78b772e9585436")
    suspend fun getPostsByTag(@Query("q") tag: String): PostModel

}

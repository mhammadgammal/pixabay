package com.mhammad.photoapp.api;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("?key=26189611-305d8ed20ad78b772e9585436")
    public Single<Response> getPosts();
}

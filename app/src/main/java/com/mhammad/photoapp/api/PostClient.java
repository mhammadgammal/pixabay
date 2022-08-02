package com.mhammad.photoapp.api;

import com.mhammad.photoapp.favouritedatabase.FavouriteDAO;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostClient {
    private static PostClient INSTANCE;
    private final static String url = "https://pixabay.com/api/";
    APIInterface apiInterface;
    public PostClient()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);
    }

    public static PostClient getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new PostClient();

        return INSTANCE;
    }
    public Single<Response> getPosts()  {return apiInterface.getPosts();}
}

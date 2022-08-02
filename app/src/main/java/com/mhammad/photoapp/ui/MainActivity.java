package com.mhammad.photoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.mhammad.photoapp.R;
import com.mhammad.photoapp.api.APIInterface;
import com.mhammad.photoapp.api.Hit;
import com.mhammad.photoapp.api.PostClient;
import com.mhammad.photoapp.api.Response;
import com.mhammad.photoapp.favouritedatabase.DataBase;
import com.mhammad.photoapp.favouritedatabase.FavouriteDAO;

import java.util.ArrayList;
import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    DataBase dp;
    List<Hit> data;
    RecyclerView postRC;
    PostAdapter adapter;
    ImageView favourite;
    final String baseUrl = "https://pixabay.com/api/";
    APIInterface apiInterface;
    FavouriteDAO favouriteDAO;
    Response response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postRC = findViewById(R.id.postRC);
        favourite = findViewById(R.id.favourite_disabled);
        dp = DataBase.getDatabase(this);
        data = new ArrayList<>();
        adapter = new PostAdapter(MainActivity.this);
        postRC.setAdapter(adapter);
        postRC.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);
        Single<Response> observable =  apiInterface.getPosts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(o-> {
            adapter.setData(o.getHits());
            data = o.getHits();
        });

        adapter.setOnFavouriteClickListener(new PostAdapter.OnFavouriteClickListener() {
            @Override
            public void onClick(int position) {
                favouriteDAO.addPost(new Hit(
                        data.get(position).largeImageURL,
                                data.get(position).views,
                                data.get(position).likes,
                                data.get(position).comments,
                                data.get(position).user,
                                data.get(position).userImageURL
                        ));
                Toast.makeText(MainActivity.this, "Added to favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
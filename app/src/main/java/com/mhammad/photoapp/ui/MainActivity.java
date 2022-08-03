package com.mhammad.photoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhammad.photoapp.R;
import com.mhammad.photoapp.adapters.PostAdapter;
import com.mhammad.photoapp.api.Hit;
import com.mhammad.photoapp.api.PostClient;
import com.mhammad.photoapp.api.Response;
import com.mhammad.photoapp.favouritedatabase.DataBase;
import com.mhammad.photoapp.favouritedatabase.FavouriteDAO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnFavoriteClickListener{
    DataBase db;
    List<Hit> data;
    RecyclerView postRC;
    PostAdapter adapter;
    ImageView favourite;
    FloatingActionButton favouriteActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postRC = findViewById(R.id.postRC);
        favourite = findViewById(R.id.favourite_disabled);
        db = DataBase.getDatabase(this);
        data = new ArrayList<>();
        adapter = new PostAdapter(MainActivity.this, this);
        postRC.setAdapter(adapter);
        postRC.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        favouriteActionButton = findViewById(R.id.favouriteBTN);
        Single<Response> observable = PostClient.getINSTANCE().getPosts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(o-> {
            adapter.setData(o.getHits());
            data = o.getHits();
        });

        favouriteActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, FavouriteActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onFavoriteClick(int position) {
        db.favouriteDAO().addPost(new Hit(
                data.get(position).largeImageURL,
                data.get(position).views,
                data.get(position).likes,
                data.get(position).comments,
                data.get(position).user,
                data.get(position).userImageURL
        ))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        Toast.makeText(MainActivity.this, "Added to favourites", Toast.LENGTH_SHORT).show();
    }

}
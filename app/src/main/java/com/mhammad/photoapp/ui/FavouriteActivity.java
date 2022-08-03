package com.mhammad.photoapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mhammad.photoapp.R;
import com.mhammad.photoapp.adapters.FavoriteAdapter;
import com.mhammad.photoapp.api.Hit;
import com.mhammad.photoapp.favouritedatabase.DataBase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouriteActivity extends AppCompatActivity {
    RecyclerView favoriteRC;
    FavoriteAdapter adapter;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        db = DataBase.getDatabase(this);
        favoriteRC = findViewById(R.id.favouriteRecycler);
        adapter = new FavoriteAdapter(this);
        favoriteRC.setAdapter(adapter);
        db.favouriteDAO().getAllPosts()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Hit>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Hit> hits) {
                        adapter.setFavorite(hits);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
package com.mhammad.photoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhammad.photoapp.R;
import com.mhammad.photoapp.api.Hit;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    List<Hit> favorite = new ArrayList<>();
    Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new FavoriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Glide.with(context).load(favorite.get(position).userImageURL).into(holder.userImg);
        Glide.with(context).load(favorite.get(position).largeImageURL).into(holder.postImg);
        holder.userName.setText(favorite.get(position).user);
    }
    public void setFavorite(List<Hit> favorite) {this.favorite = favorite;notifyDataSetChanged();}
    @Override
    public int getItemCount() {
        return favorite.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg, postImg;
        TextView userName;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.usrImg);
            postImg = itemView.findViewById(R.id.favouritePostImg);
            userName = itemView.findViewById(R.id.usrNamTV);
        }
    }
}

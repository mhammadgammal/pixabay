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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Hit> data = new ArrayList<>();
    private Context context;
    private static OnFavoriteClickListener listener;
    public PostAdapter(Context context, OnFavoriteClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Hit> data) {this.data = data;notifyDataSetChanged();}

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);

        return new PostViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).userImageURL).into(holder.userImg);
        Glide.with(context).load(data.get(position).largeImageURL).into(holder.postImg);
        holder.userName.setText(data.get(position).user);
        holder.like.setText(String.valueOf(data.get(position).likes));
        holder.comment.setText(String.valueOf(data.get(position).comments));
        holder.views.setText(String.valueOf(data.get(position).views));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg, postImg, favourite;
        TextView views, comment, like, userName;
        OnFavoriteClickListener listener;
        public PostViewHolder(@NonNull View itemView, OnFavoriteClickListener listener) {
            super(itemView);
            this.listener = listener;
            userImg = itemView.findViewById(R.id.userImg);
            postImg = itemView.findViewById(R.id.postImg);
            views = itemView.findViewById(R.id.viewsTV);
            comment = itemView.findViewById(R.id.commentTV);
            like = itemView.findViewById(R.id.likeTV);
            userName = itemView.findViewById(R.id.userNameTV);
            favourite =  itemView.findViewById(R.id.favourite_disabled);
           favourite.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   listener.onFavoriteClick(getAdapterPosition());
               }
           });
        }


    }
    public interface OnFavoriteClickListener{
        void onFavoriteClick(int position);
    }
}

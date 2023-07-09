package com.example.pixabay.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabay.R
import com.example.pixabay.databinding.PostItemBinding
import com.example.pixabay.model.Hit

class PostsAdapter(private val clickListener: PostClickListener)
    : ListAdapter<Hit, PostsAdapter.PostsViewHolder>(PostsDiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        Log.d(TAG, "onCreateViewHolder: fetched")
        return PostsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
        Log.d(TAG, "onBindViewHolder: fetched")
    }

    class PostsViewHolder private constructor(private val postItemBinding: PostItemBinding):
        RecyclerView.ViewHolder(postItemBinding.root) {
        fun bind(item: Hit, clickListener: PostClickListener){
            postItemBinding.apply {
                post = item
                click = clickListener
                if (item.isFavourite) likeIcon.setImageResource(R.drawable.favorite)
                if (!item.isFavourite) likeIcon.setImageResource(R.drawable.outline_favorite_border_24)
                executePendingBindings()
            }
        }

        companion object{
            fun from(parent: ViewGroup): PostsViewHolder{
                val binding: PostItemBinding = PostItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PostsViewHolder(binding)
            }
        }
    }

    class PostClickListener(private val clickListener: (post: Hit) -> Unit) {
        fun onClick(post: Hit) = clickListener(post)
    }

    class PostsDiffCallBack : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return newItem == oldItem
        }
    }

    companion object{
        private const val TAG = "PostsAdapter"

    }
}
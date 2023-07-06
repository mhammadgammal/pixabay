package com.example.pixabay.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabay.R
import com.example.pixabay.databinding.FavouriteItemBinding
import com.example.pixabay.model.FavouritePost

@SuppressLint("StaticFieldLeak")
lateinit var binding: FavouriteItemBinding
class FavouriteAdapter(private val clickListener: FavouriteOnClickListener):
ListAdapter<FavouritePost, FavouriteAdapter.FavouriteViewHolder>(FavouritePostsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
        Log.d(TAG, "onBindViewHolder: fetched")
    }


    class FavouriteViewHolder(private val binding: FavouriteItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: FavouritePost, clickListener: FavouriteOnClickListener){
            binding.apply {
                post = item
                click = clickListener
                likeIcon.setImageResource(R.drawable.favorite)
                executePendingBindings()
            }
        }

        companion object{
            fun from(parent: ViewGroup): FavouriteViewHolder {
                val binding: FavouriteItemBinding = FavouriteItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FavouriteViewHolder(binding)
            }
        }

    }


    class FavouriteOnClickListener(private val clickListener: (post: FavouritePost, binding: FavouriteItemBinding) -> Unit){
        fun onCLick(post: FavouritePost) = clickListener(post, binding)
    }

    class FavouritePostsDiffCallback: DiffUtil.ItemCallback<FavouritePost>() {
        override fun areItemsTheSame(oldItem: FavouritePost, newItem: FavouritePost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavouritePost, newItem: FavouritePost): Boolean {
            return newItem == oldItem
        }
    }

    companion object{
        private const val TAG = "FavouriteAdapter"
    }
}
package com.example.pixabay

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load


@BindingAdapter("setUserPhoto")
fun downloadUserPhoto(imageView: ImageView, url: String?) {
    imageView.load(url){
        placeholder(R.drawable.loading_animation)
        error(R.drawable.ic_broken_image)
    }
}

@BindingAdapter("setPostPic")
fun downloadPostPic(imageView: ImageView, url: String?) {
    imageView.load(url)
}

@BindingAdapter("setFavIcon")
fun setFavIcon(imageView: ImageView, isFavourite: Boolean){
    if (isFavourite){
        imageView.setImageResource(R.drawable.favorite)
    }else imageView.setImageResource(R.drawable.outline_favorite_border_24)
}

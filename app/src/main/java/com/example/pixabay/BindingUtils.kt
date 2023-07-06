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
    imageView.load(url){
        placeholder(R.drawable.loading_animation)
        error(R.drawable.ic_broken_image)
    }
}

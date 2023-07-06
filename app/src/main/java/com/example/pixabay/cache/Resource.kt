package com.example.pixabay.cache

import com.example.pixabay.model.Hit

sealed class Resource(
    val data: List<Hit>,
    val throwable: Throwable? = null
){
    class SUCCESS(data: List<Hit>): Resource(data)
    class LOADING(data: List<Hit>): Resource(data)
    class ERROR(data: List<Hit>, throwable: Throwable): Resource(data, throwable)

}

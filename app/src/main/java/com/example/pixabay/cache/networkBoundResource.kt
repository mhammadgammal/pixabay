package com.example.pixabay.cache

import android.content.Context
import android.util.Log
import com.example.pixabay.model.Hit
import com.example.pixabay.model.PostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

const val TAG = "networkBoundResource"
fun networkBoundResource(
    query: () -> Flow<List<Hit>>,
    fetch: suspend () -> PostModel,
    saveFetchResult: suspend (List<Hit>) -> Unit,
    shouldFetch: Boolean
): Flow<Resource> = flow {
    val data = query().first()
    val resource = if(shouldFetch){
        emit(Resource.LOADING(data))

        try {
            val resultType = fetch()

            saveFetchResult(resultType.hits)

            query().map { Resource.SUCCESS(it) }

        }catch (throwable: Throwable){query().map{Resource.ERROR(it, throwable)}}
    } else {query().map { Resource.SUCCESS(it) }}

    emitAll(resource)
}
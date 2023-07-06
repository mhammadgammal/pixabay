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
    shouldFetch: Boolean = true
): Flow<Resource> {
    Log.d(TAG, "networkBoundResource: fired")

    return flow {
        val data = query().first()
        emit(Resource.LOADING(data))
        val resource = try {
            val fetchedData = fetch().hits
            saveFetchResult(fetchedData)
            query().map {
                Resource.SUCCESS(data)
            }
        } catch (th: Throwable) {
            query().map {
                Resource.ERROR(data, th)
            }
        }

        emitAll(resource)
    }

}
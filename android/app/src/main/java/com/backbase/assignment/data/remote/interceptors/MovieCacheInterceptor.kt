package com.backbase.assignment.data.remote.interceptors

import com.backbase.assignment.common.Constants
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit


class MovieCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        val updatedRequest = getUpdatedCachedRequest(request)

        return chain.proceed(updatedRequest)
    }

    private fun getUpdatedCachedRequest(request: Request): Request {

        /*
         * keep getting from the cache withing the time frame
         */
        if (shouldCacheRequestFromEndpoint(request)) {
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.MINUTES)
                .build()

            return request.newBuilder()
                .addHeader("Cache-Control", cacheControl.toString())
                .build()
        }

        return request.newBuilder().build()
    }

    private fun shouldCacheRequestFromEndpoint(request: Request): Boolean {
        val requestUrl = request.url.toString()

        if (requestUrl.contains(Constants.ENDPOINT_MOVIE_DETAILS) ||
            request.url.toString().contains(Constants.ENDPOINT_MOST_POPULAR_MOVIES) ) {
            return true
        }

        return false
    }

}
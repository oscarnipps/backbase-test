package com.backbase.assignment

import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TestServiceGenerator {

    companion object {

        fun <T> getService(serviceClass: Class<T>, url: String, port : Int): T {
            return Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(getGsonConverterFactory())
                .client(getClient(port))
                .build()
                .create(serviceClass)
        }

        private fun getGsonConverterFactory(): GsonConverterFactory {
            val gson = GsonBuilder()
                .create()

            return GsonConverterFactory.create(gson)
        }

        private fun getClient(port: Int): OkHttpClient {
            val client = OkHttpClient
                .Builder()
                .hostnameVerifier { hostname, session -> true }
                .addInterceptor { chain ->
                    var request: Request = chain.request()

                    val httpUrl: HttpUrl = request.url.newBuilder()
                        .scheme("http")
                        .host("localhost")
                        .port(port)
                        .build()

                    request = request.newBuilder()
                        .url(httpUrl)
                        .build()

                    return@addInterceptor chain.proceed(request)
                }

            return client.build()
        }
    }

}
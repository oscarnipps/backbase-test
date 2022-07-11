package com.backbase.assignment.di

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.remote.interceptors.MovieCacheInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.API_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        builder.addInterceptor(loggingInterceptor)

        builder.addNetworkInterceptor(MovieCacheInterceptor())

        return builder
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .create()

        return GsonConverterFactory.create(gson)
    }
}
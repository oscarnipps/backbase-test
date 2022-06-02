package com.backbase.assignment.di

import com.backbase.assignment.data.mappers.MostPopularMovieMapper
import com.backbase.assignment.data.mappers.NowPlayingMovieMapper
import com.backbase.assignment.data.remote.httpservice.MostPopularMovieService
import com.backbase.assignment.data.remote.httpservice.NowPlayingService
import com.backbase.assignment.data.repo.NowPlayingRepo
import com.backbase.assignment.data.repo.NowPlayingRepoImpl
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepo
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NowPlayingModule {

    @Provides
    @Singleton
    fun provideNowPlayingMovieService(retrofit : Retrofit) : NowPlayingService {
        return retrofit.create(NowPlayingService::class.java)
    }


    @Provides
    @Singleton
    fun provideNowPlayingMovieRepo(nowPlayingRepoImpl: NowPlayingRepoImpl) : NowPlayingRepo {
        return nowPlayingRepoImpl
    }

    @Provides
    @Singleton
    fun provideNowPlayingMovieMapper() : NowPlayingMovieMapper {
        return NowPlayingMovieMapper()
    }
}
package com.backbase.assignment.di

import com.backbase.assignment.data.mappers.MostPopularMovieMapper
import com.backbase.assignment.data.mappers.MovieDetailMapper
import com.backbase.assignment.data.remote.httpservice.MostPopularMovieService
import com.backbase.assignment.data.remote.httpservice.MovieDetailService
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepo
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepoImpl
import com.backbase.assignment.data.repo.moviedetail.MovieDetailRepo
import com.backbase.assignment.data.repo.moviedetail.MovieDetailRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieDetailModule {

    @Provides
    @Singleton
    fun provideMovieDetailService(retrofit : Retrofit) : MovieDetailService {
        return retrofit.create(MovieDetailService::class.java)
    }


    @Provides
    @Singleton
    fun provideMovieDetailRepo(movieDetailRepoImpl: MovieDetailRepoImpl) : MovieDetailRepo {
        return movieDetailRepoImpl
    }

    @Provides
    @Singleton
    fun provideMovieDetailMapper() : MovieDetailMapper {
        return MovieDetailMapper()
    }
}
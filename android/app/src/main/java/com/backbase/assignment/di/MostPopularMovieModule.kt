package com.backbase.assignment.di

import com.backbase.assignment.data.mappers.MostPopularMovieMapper
import com.backbase.assignment.data.remote.httpservice.MostPopularMovieService
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
class MostPopularMovieModule {

    @Provides
    @Singleton
    fun provideMostPopularMovieService(retrofit :Retrofit) : MostPopularMovieService {
        return retrofit.create(MostPopularMovieService::class.java)
    }


    @Provides
    @Singleton
    fun provideMostPopularMovieRepo(mostPopularRepoImpl: MostPopularRepoImpl) : MostPopularRepo {
        return mostPopularRepoImpl
    }

    @Provides
    @Singleton
    fun provideMostPopularMovieMapper() : MostPopularMovieMapper {
        return MostPopularMovieMapper()
    }

}
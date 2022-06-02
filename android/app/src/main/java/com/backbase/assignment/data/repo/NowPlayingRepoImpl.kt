package com.backbase.assignment.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.backbase.assignment.data.mappers.MovieDetailMapper
import com.backbase.assignment.data.mappers.NowPlayingMovieMapper
import com.backbase.assignment.data.paging.NowPlayingPagingSource
import com.backbase.assignment.data.remote.httpservice.MovieDetailService
import com.backbase.assignment.data.remote.httpservice.NowPlayingService
import com.backbase.assignment.data.repo.moviedetail.MovieDetailRepo
import com.backbase.assignment.ui.playingnow.NowPlayingMovie
import io.reactivex.Flowable
import javax.inject.Inject

class NowPlayingRepoImpl @Inject constructor(
    private val nowPlayingService: NowPlayingService,
    private val mapper: NowPlayingMovieMapper
) : NowPlayingRepo {

    override fun getMovies(): Flowable<PagingData<NowPlayingMovie>> {
        val pagingSource = NowPlayingPagingSource(nowPlayingService, mapper)

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }

}
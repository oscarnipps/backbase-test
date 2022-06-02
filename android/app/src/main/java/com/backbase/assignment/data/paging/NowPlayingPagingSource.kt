package com.backbase.assignment.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.mappers.NowPlayingMovieMapper
import com.backbase.assignment.data.remote.httpservice.NowPlayingService
import com.backbase.assignment.ui.playingnow.NowPlayingMovie
import com.backbase.assignment.ui.playingnow.NowPlayingMovies
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingPagingSource @Inject constructor (
    private val service: NowPlayingService,
    private val mapper: NowPlayingMovieMapper
) : RxPagingSource<Int, NowPlayingMovie>() {

    override fun getRefreshKey(state: PagingState<Int, NowPlayingMovie>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, NowPlayingMovie>> {
        val position = params.key ?: 1

        val queryMap = mutableMapOf(
            Pair(Constants.QUERY_API_KEY, Constants.API_KEY),
            Pair(Constants.QUERY_LANGUAGE_KEY, Constants.LANGUAGE),
        )

        return service.getNowPlayingMovies(queryMap)
            .subscribeOn(Schedulers.io())
            .map { mapper.mapToNowPlayingMovie(it) }
            .map{toLoadResult(it,position)}
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: NowPlayingMovies, position : Int) : LoadResult<Int, NowPlayingMovie> {
        return LoadResult.Page(
            data = data.movies,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.total) null else position + 1
        )
    }

}
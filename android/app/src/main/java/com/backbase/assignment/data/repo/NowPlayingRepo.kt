package com.backbase.assignment.data.repo

import androidx.paging.PagingData
import com.backbase.assignment.ui.playingnow.NowPlayingMovie
import io.reactivex.Flowable

interface NowPlayingRepo {
    fun getMovies(): Flowable<PagingData<NowPlayingMovie>>

}
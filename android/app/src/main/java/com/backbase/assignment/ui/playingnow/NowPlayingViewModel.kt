package com.backbase.assignment.ui.playingnow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.backbase.assignment.common.Resource
import com.backbase.assignment.data.repo.NowPlayingRepo
import com.backbase.assignment.util.ErrorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val nowPlayingRepo: NowPlayingRepo
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val nowPlayingMovie: MutableLiveData<Resource<PagingData<NowPlayingMovie>>> = MutableLiveData()

    fun getFavoriteMovies() {
        nowPlayingMovie.value = Resource.loading()

        compositeDisposable.add(
            nowPlayingRepo
                .getMovies()
                .cachedIn(viewModelScope)
                .subscribe(
                    { movies -> handlePagedMovies(movies) },
                    { error -> handlePagedMovieError(error) }
                )
        )
    }

    fun getNowPlayingMovie(): LiveData<Resource<PagingData<NowPlayingMovie>>> {
        return nowPlayingMovie
    }

    private fun handlePagedMovieError(error: Throwable) {
        Timber.e(error.localizedMessage)
        nowPlayingMovie.value = Resource.error(ErrorUtil.getErrorMessage(error))
    }

    private fun handlePagedMovies(movies: PagingData<NowPlayingMovie>) {
        nowPlayingMovie.value = Resource.success(movies)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
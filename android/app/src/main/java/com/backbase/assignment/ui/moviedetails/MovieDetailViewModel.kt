package com.backbase.assignment.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.backbase.assignment.common.Resource
import com.backbase.assignment.data.repo.moviedetail.MovieDetailRepo
import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import com.backbase.assignment.util.ErrorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailRepo: MovieDetailRepo
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val movieDetail: MutableLiveData<Resource<MovieDetail>> = MutableLiveData()

    fun getMovieDetails(movieId: String) {
        movieDetail.value = Resource.loading()

        compositeDisposable.add(
            movieDetailRepo.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movieDetail -> handleMovieDetail(movieDetail) },
                    { error -> handleMovieDetailError(error) }
                )
        )
    }

    private fun handleMovieDetailError(error: Throwable) {
        Timber.e(error.localizedMessage)
        movieDetail.value = Resource.error(ErrorUtil.getErrorMessage(error))
    }

    private fun handleMovieDetail(movieDetail: MovieDetail) {
        this.movieDetail.value = Resource.success(movieDetail)
    }

    fun getMovieDetail(): LiveData<Resource<MovieDetail>> {
        return movieDetail
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
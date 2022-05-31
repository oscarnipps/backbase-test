package com.backbase.assignment.ui.mostpopular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.backbase.assignment.common.Resource
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MostPopularViewModel @Inject constructor(
    private val mostPopularRepo: MostPopularRepo
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val popularMovies: MutableLiveData<Resource<List<MostPopularMovie>>> = MutableLiveData()


    fun getMostPopularMovies() {
        popularMovies.value = Resource.loading()

        compositeDisposable.add(
            mostPopularRepo.getMostPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movieItems -> handleMostPopularMovies(movieItems) },
                    { error -> handleMostPopularMoviesError(error) }
                )
        )
    }

    private fun handleMostPopularMoviesError(error: Throwable) {
        Timber.e(error.localizedMessage)

        popularMovies.value = Resource.error("error loading popular movies")

    }

    private fun handleMostPopularMovies(movieItems: List<MostPopularMovie>) {
        popularMovies.value = Resource.success(movieItems)
    }

    fun mostPopularMovies(): LiveData<Resource<List<MostPopularMovie>>> {
        return  popularMovies
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
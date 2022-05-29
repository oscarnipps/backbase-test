package com.backbase.assignment.ui.mostpopular

import androidx.lifecycle.ViewModel
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MostPopularViewModel @Inject constructor(private val mostPopularRepo: MostPopularRepo) :
    ViewModel() {


}
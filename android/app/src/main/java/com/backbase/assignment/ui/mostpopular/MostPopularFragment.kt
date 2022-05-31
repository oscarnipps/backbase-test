package com.backbase.assignment.ui.mostpopular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.backbase.assignment.R
import com.backbase.assignment.common.Resource
import com.backbase.assignment.databinding.FragmentMostPopularBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MostPopularFragment   : Fragment() {

    private lateinit var binding : FragmentMostPopularBinding
    private val viewModel : MostPopularViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_most_popular, container, false)

        viewModel.getMostPopularMovies()

        viewModel.mostPopularMovies().observe(viewLifecycleOwner,mostPopularMoviesObserver())

        return binding.root
    }

    private fun mostPopularMoviesObserver(): Observer<Resource<List<MostPopularMovie>>> {
        return Observer { movies ->

            when (movies.status) {

                Resource.Status.LOADING -> {
                    Timber.d("loading data from network")
                }

                Resource.Status.SUCCESS -> {
                    Timber.d("movies retrieved with size : ${movies.data?.size}")
                }

                Resource.Status.ERROR -> {
                    Timber.e(movies.message)
                }
            }
        }
    }
}
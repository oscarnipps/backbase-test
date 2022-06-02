package com.backbase.assignment.ui.playingnow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.backbase.assignment.R
import com.backbase.assignment.common.Resource
import com.backbase.assignment.databinding.FragmentPlayingNowBinding
import com.backbase.assignment.ui.moviedetails.MovieDetailViewModel
import com.backbase.assignment.util.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PlayingNowFragment  : Fragment() {

    private lateinit var binding : FragmentPlayingNowBinding
    private val viewModel: NowPlayingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playing_now, container, false)

        viewModel.getFavoriteMovies()

        viewModel.getNowPlayingMovie().observe(viewLifecycleOwner, nowPlayingObserver())

        return binding.root
    }

    private fun nowPlayingObserver(): Observer<Resource<PagingData<NowPlayingMovie>>> {
        return Observer { result ->

            when (result.status) {

                Resource.Status.LOADING -> {
                    Timber.d("loading data")
                }

                Resource.Status.SUCCESS -> {
                    Timber.d("data retreived ${result.data}")
                }

                Resource.Status.ERROR -> {

                    Timber.d("error loading data")

                }
            }
        }
    }
}
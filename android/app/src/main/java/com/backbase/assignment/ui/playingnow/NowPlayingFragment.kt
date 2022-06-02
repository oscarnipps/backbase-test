package com.backbase.assignment.ui.playingnow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.common.Resource
import com.backbase.assignment.databinding.FragmentPlayingNowBinding
import com.backbase.assignment.ui.mostpopular.MostPopularFragmentDirections
import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import com.backbase.assignment.ui.moviedetails.MovieDetailViewModel
import com.backbase.assignment.util.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NowPlayingFragment  : Fragment() , NowPlayingMoviesAdapter.NowPlayingMovieItemInterface {

    private lateinit var binding : FragmentPlayingNowBinding
    private val viewModel: NowPlayingViewModel by viewModels()
    private lateinit var mAdapter: NowPlayingMoviesAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playing_now, container, false)

        recyclerView = binding.list

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        mAdapter = NowPlayingMoviesAdapter(this)

        recyclerView.adapter = mAdapter

        EspressoIdlingResource.increment()

        viewModel.getFavoriteMovies()

        viewModel.getNowPlayingMovie().observe(viewLifecycleOwner, nowPlayingObserver())

        return binding.root
    }

    private fun nowPlayingObserver(): Observer<Resource<PagingData<NowPlayingMovie>>> {
        return Observer { result ->

            when (result.status) {

                Resource.Status.LOADING -> {
                    Timber.d("loading data")

                    binding.loadingView.visibility = View.VISIBLE
                }

                Resource.Status.SUCCESS -> {
                    binding.loadingView.visibility = View.GONE

                    binding.errorView.visibility = View.GONE

                    Timber.d("data retreived ${result.data}")

                    mAdapter.submitData(lifecycle,result.data!!)

                    EspressoIdlingResource.decrement()
                }

                Resource.Status.ERROR -> {
                    EspressoIdlingResource.decrement()

                    Timber.e(result.message)

                    binding.loadingView.visibility = View.GONE

                    binding.errorView.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onMovieItemClicked(movie: NowPlayingMovie?) {
        Timber.d("movie id : ${movie?.movieId}")
        navigateToMovieDetails(movie)
    }


    private fun navigateToMovieDetails(movie: NowPlayingMovie?) {
        val directions =
            NowPlayingFragmentDirections.actionPlayingNowFragmentToMovieDetailFragment(
                movie?.movieId.toString()
            )

        findNavController().navigate(directions)
    }
}
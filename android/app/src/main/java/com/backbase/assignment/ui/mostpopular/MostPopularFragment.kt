package com.backbase.assignment.ui.mostpopular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.backbase.assignment.R
import com.backbase.assignment.common.Resource
import com.backbase.assignment.databinding.FragmentMostPopularBinding
import com.backbase.assignment.util.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MostPopularFragment : Fragment(), MostPopularMoviesAdapter.MovieItemInterface {

    private lateinit var binding: FragmentMostPopularBinding
    private lateinit var viewPager: ViewPager2
    private val viewModel: MostPopularViewModel by viewModels()
    private var popularMovies = emptyList<MostPopularMovie>()
    private lateinit var currentMovie: MostPopularMovie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_most_popular, container, false)

        EspressoIdlingResource.increment()

        viewModel.getMostPopularMovies()

        viewModel.mostPopularMovies().observe(viewLifecycleOwner, mostPopularMoviesObserver())

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewPager = binding.popularMoviesViewPager

        binding.seeMore.setOnClickListener { navigateToMovieDetails(currentMovie) }

        setUpViewPager()

        return binding.root
    }

    private fun setUpViewPager() {
        with(viewPager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)

        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

        viewPager.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2

            val offset = position * -(2 * offsetPx + pageMarginPx)

            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {

                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }

            } else {
                page.translationY = offset
            }
        }

        viewPager.registerOnPageChangeCallback(getOnPageChangeCallback())
    }

    private fun getOnPageChangeCallback() = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            currentMovie = popularMovies[position]

            viewModel.setCurrentPopularMovie(currentMovie)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)

            when (state) {
                ViewPager2.SCROLL_STATE_IDLE -> {
                    binding.movieDetailGroup.visibility = View.VISIBLE
                }

                ViewPager2.SCROLL_STATE_DRAGGING -> {
                    //todo: show movie detail text loading  animation

                    binding.movieDetailGroup.visibility = View.GONE
                }

                ViewPager2.SCROLL_STATE_SETTLING -> {
                    //todo: hide movie detail text loading  animation
                }
            }
        }
    }

    private fun mostPopularMoviesObserver(): Observer<Resource<List<MostPopularMovie>>> {
        return Observer { movies ->

            when (movies.status) {

                Resource.Status.LOADING -> {
                    binding.loadingView.visibility = View.VISIBLE
                }

                Resource.Status.SUCCESS -> {
                    binding.loadingView.visibility = View.GONE

                    binding.errorView.visibility = View.GONE

                    val movieList = movies.data!!

                    Timber.d("movies retrieved with size : ${movies.data.size}")

                    viewPager.adapter = MostPopularMoviesAdapter(this, movieList)

                    popularMovies = movieList

                    EspressoIdlingResource.decrement()
                }

                Resource.Status.ERROR -> {
                    EspressoIdlingResource.decrement()

                    Timber.e(movies.message)

                    binding.loadingView.visibility = View.GONE

                    binding.errorView.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onPopularMovieItemClicked(movie: MostPopularMovie) {
        Timber.d("movie id : ${movie.movieId}")
        navigateToMovieDetails(movie)
    }

    private fun navigateToMovieDetails(movie: MostPopularMovie) {
        val directions =
            MostPopularFragmentDirections.actionMostPopularFragmentToMovieDetailFragment(
                movie.movieId.toString()
            )

        findNavController().navigate(directions)
    }
}
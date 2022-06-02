package com.backbase.assignment.ui.moviedetails

import android.content.res.Resources
import android.os.Bundle
import android.text.Layout
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.backbase.assignment.R
import com.backbase.assignment.common.Resource
import com.backbase.assignment.databinding.FragmentMovieDetailBinding
import com.backbase.assignment.ui.mostpopular.MostPopularViewModel
import com.backbase.assignment.util.EspressoIdlingResource
import com.google.android.material.internal.ViewUtils.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)

        EspressoIdlingResource.increment()

        viewModel.getMovieDetails(args.movieId)

        viewModel.getMovieDetail().observe(viewLifecycleOwner, movieDetailObserver())

        binding.lifecycleOwner = this

        binding.close.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }

    private fun movieDetailObserver(): Observer<Resource<MovieDetail>> {
        return Observer { result ->

            when (result.status) {

                Resource.Status.LOADING -> {
                    binding.loadingView.visibility = View.VISIBLE
                }

                Resource.Status.SUCCESS -> {
                    binding.loadingView.visibility = View.GONE

                    binding.errorView.visibility = View.GONE

                    val movie = result.data!!

                    binding.movie = movie

                    setUpMovieGenres(movie.genreList)

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

    private fun setUpMovieGenres(genreList: List<String>) {
        for (genre in genreList) {
            binding.genreContainer.addView(getChildTextView(genre))
        }
    }

    private fun getChildTextView(genre: String): TextView {
        val childTextView = TextView(requireContext())

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(
            R.dimen.zero_margin,
            R.dimen.genre_margin_right,
            R.dimen.genre_margin_top,
            R.dimen.genre_margin_bottom
        )

        childTextView.apply {
            //childTextView.layoutParams = params

            text = genre

            setPadding(10)

            setTextColor(resources.getColor(R.color.genre_text_color, null))

            isAllCaps = true

            setTextSize( TypedValue.COMPLEX_UNIT_SP, 14f)

            background = ResourcesCompat.getDrawable(resources, R.drawable.bg_genre, null)
        }

        return childTextView
    }
}
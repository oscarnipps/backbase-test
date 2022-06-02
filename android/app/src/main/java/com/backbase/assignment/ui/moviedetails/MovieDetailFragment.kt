package com.backbase.assignment.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.backbase.assignment.R
import com.backbase.assignment.databinding.FragmentMovieDetailBinding
import com.backbase.assignment.ui.mostpopular.MostPopularViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding : FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private val args : MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)

        viewModel.getMovieDetails(args.movieId)

        return binding.root
    }
}
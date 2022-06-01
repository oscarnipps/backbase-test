package com.backbase.assignment.ui.mostpopular

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.bumptech.glide.Glide

//todo: change to use diff util and list adapter
class MostPopularMoviesAdapter(
    private val itemInterface: MovieItemInterface,
    private var popularMovies: List<MostPopularMovie>
) : RecyclerView.Adapter<MostPopularMoviesAdapter.MostPopularMoviesViewHolder>() {


    interface MovieItemInterface {
        fun onPopularMovieItemClicked(movie: MostPopularMovie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularMoviesViewHolder {
        return MostPopularMoviesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.popular_movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MostPopularMoviesViewHolder, position: Int) {
        val popularMovie = popularMovies[position]

        holder.bind(popularMovie)
    }

    override fun getItemCount(): Int {
        return popularMovies.size
    }


    inner class MostPopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        var movieImageView: ImageView = itemView.findViewById(R.id.movie_image)

        override fun onClick(v: View) {
            itemInterface.onPopularMovieItemClicked(popularMovies[bindingAdapterPosition])
        }

        fun bind(popularMovie: MostPopularMovie) {
            Glide.with(itemView.context).load(popularMovie.movieImageUrl).into(movieImageView)
        }

    }

}
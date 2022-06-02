package com.backbase.assignment.ui.playingnow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.databinding.NowPlayingGridItemBinding
import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import com.backbase.assignment.ui.mostpopular.MostPopularMoviesAdapter
import com.bumptech.glide.Glide

class NowPlayingMoviesAdapter(
    private val itemInterface: NowPlayingMovieItemInterface,
) : PagingDataAdapter<NowPlayingMovie, NowPlayingMoviesAdapter.NowPlayingViewHolder>(COMPARATOR) {


    interface NowPlayingMovieItemInterface {
        fun onMovieItemClicked(movie: NowPlayingMovie?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val binding = DataBindingUtil.inflate<NowPlayingGridItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.now_playing_grid_item,
            parent,
            false
        )

        return NowPlayingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<NowPlayingMovie>() {

            override fun areItemsTheSame(
                oldItem: NowPlayingMovie,
                newItem: NowPlayingMovie
            ): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(
                oldItem: NowPlayingMovie,
                newItem: NowPlayingMovie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class NowPlayingViewHolder(private val itemBinding: NowPlayingGridItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private var currentItem: NowPlayingMovie? = null

        fun bind(movie: NowPlayingMovie) {
            itemBinding.movie = movie

            currentItem = movie
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemInterface.onMovieItemClicked(currentItem)
        }
    }
}
package com.aydee.movieapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aydee.movieapplication.databinding.ItemMovieBinding
import com.aydee.movieapplication.model.Search
import com.bumptech.glide.Glide

class MovieRVAdapter(
    private val movieList: MutableList<Search>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<MovieRVAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieRVAdapter.MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieRVAdapter.MovieViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = movieList.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : ViewHolder(binding.root) {
        fun bind(position: Int) {
            val movie = movieList[position]
            binding.txtMovieTitle.text = movie.Title
            binding.txtYear.text = movie.Year
            Glide.with(binding.root.context).load(movie.Poster).into(binding.imgPoster)

            binding.root.setOnClickListener {
                listener.onClick(movie.imdbID)
            }
        }
    }
}

interface OnClickListener {
    fun onClick(imdbID: String)
}
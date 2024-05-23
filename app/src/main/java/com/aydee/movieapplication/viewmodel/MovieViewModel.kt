package com.aydee.movieapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aydee.movieapplication.model.DescriptionResponse
import com.aydee.movieapplication.model.MovieResponse
import com.aydee.movieapplication.repository.MovieRepository
import com.aydee.movieapplication.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    val movieLiveData: LiveData<NetworkResult<MovieResponse>>
        get() = movieRepository.movieLiveData

    val movieDescLiveData: LiveData<NetworkResult<DescriptionResponse>>
        get() = movieRepository.movieDescLiveData

    fun fetchMovies(movieName: String) {
        viewModelScope.launch {
            movieRepository.fetchMovie(movieName)
        }
    }

    fun fetchMovieDescription(imdbId: String) {
        viewModelScope.launch {
            movieRepository.fetchMovieDescription(imdbId)
        }
    }
}
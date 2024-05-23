package com.aydee.movieapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aydee.movieapplication.api.MovieAPI
import com.aydee.movieapplication.model.DescriptionResponse
import com.aydee.movieapplication.model.MovieResponse
import com.aydee.movieapplication.utils.Constants
import com.aydee.movieapplication.utils.NetworkResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieAPI: MovieAPI) {
    private val _movieLiveData = MutableLiveData<NetworkResult<MovieResponse>>()
    val movieLiveData: LiveData<NetworkResult<MovieResponse>>
        get() = _movieLiveData

    private val _movieDescLiveData = MutableLiveData<NetworkResult<DescriptionResponse>>()
    val movieDescLiveData: LiveData<NetworkResult<DescriptionResponse>>
        get() = _movieDescLiveData

    fun fetchMovie(movieName: String) {
        _movieLiveData.postValue(NetworkResult.Loading())
        val response = movieAPI.fetchMovies(Constants.API_KEY, movieName)
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(p0: Call<MovieResponse>, p1: Response<MovieResponse>) {
                _movieLiveData.postValue(NetworkResult.Success(p1.body()))
            }

            override fun onFailure(p0: Call<MovieResponse>, p1: Throwable) {
                _movieLiveData.postValue(NetworkResult.Error("Error"))
            }
        })
    }

    fun fetchMovieDescription(imdbId: String) {
        _movieDescLiveData.postValue(NetworkResult.Loading())
        val response = movieAPI.fetchMovieDescription(Constants.API_KEY, imdbId)
        response.enqueue(object : Callback<DescriptionResponse> {
            override fun onResponse(
                p0: Call<DescriptionResponse>,
                p1: Response<DescriptionResponse>
            ) {
                _movieDescLiveData.postValue(NetworkResult.Success(p1.body()))
            }

            override fun onFailure(p0: Call<DescriptionResponse>, p1: Throwable) {
                _movieDescLiveData.postValue(NetworkResult.Error("Error"))
            }
        })
    }
}
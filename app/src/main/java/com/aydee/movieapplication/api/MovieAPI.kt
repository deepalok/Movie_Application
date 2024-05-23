package com.aydee.movieapplication.api

import com.aydee.movieapplication.model.DescriptionResponse
import com.aydee.movieapplication.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("/")
    fun fetchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") movieName: String
    ): Call<MovieResponse>

    @GET("/")
    fun fetchMovieDescription(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Call<DescriptionResponse>
}
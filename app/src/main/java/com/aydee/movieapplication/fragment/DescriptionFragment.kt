package com.aydee.movieapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aydee.movieapplication.databinding.FragmentDescriptionBinding
import com.aydee.movieapplication.utils.Constants
import com.aydee.movieapplication.utils.NetworkResult
import com.aydee.movieapplication.viewmodel.MovieViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionFragment : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private var imdbID: String = ""
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDescriptionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getting imdb id from intent
        imdbID = arguments?.getString(Constants.IMDB_ID).toString()
        // fetches details of movie by using imdb id
        movieViewModel.fetchMovieDescription(imdbID)
        // observes livedata
        observers()
    }

    private fun observers() {
        movieViewModel.movieDescLiveData.observe(viewLifecycleOwner) {
            // make loader invisible here
            binding.progressBar2.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    // binding data received from the request
                    binding.txtDescMovieName.text = it.data!!.Title
                    binding.txtDesc.text = it.data.Plot
                    Glide.with(binding.imgDescPoster.context).load(it.data.Poster)
                        .into(binding.imgDescPoster)
                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.progressBar2.isVisible = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
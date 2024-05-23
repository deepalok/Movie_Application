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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aydee.movieapplication.R
import com.aydee.movieapplication.adapter.MovieRVAdapter
import com.aydee.movieapplication.adapter.OnClickListener
import com.aydee.movieapplication.databinding.FragmentMainBinding
import com.aydee.movieapplication.model.Search
import com.aydee.movieapplication.utils.Constants
import com.aydee.movieapplication.utils.NetworkResult
import com.aydee.movieapplication.viewmodel.MovieViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), OnClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieRVAdapter
    private var movieList: MutableList<Search>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieList = mutableListOf()

        // set recycler view adapter
        setAdapter()
        // initially fetches movies
        movieViewModel.fetchMovies("movies")

        // search movie
        binding.btnSearch.setOnClickListener {
            // clearing the list to get the fresh data
            movieList!!.clear()
            // get text from edit text view
            val movieName = binding.etSearchMovie.text.toString()
            // movie will be fetches as per the search
            movieViewModel.fetchMovies(movieName)
        }

        // observe the live data
        observers()
    }

    private fun observers() {
        movieViewModel.movieLiveData.observe(viewLifecycleOwner) {
            // make loader invisible here
            binding.progressBar.isVisible = false
            when (it) {
                // if response is successful
                is NetworkResult.Success -> {
                    movieList!!.addAll(it.data!!.Search)
                    adapter.notifyDataSetChanged()
                }
                // api fetching gets error
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
                // loading state
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = MovieRVAdapter(movieList!!, this)
        binding.movieListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.movieListRV.adapter = adapter
    }

    override fun onClick(imdbID: String) {
        val bundle = Bundle()
        bundle.putString(Constants.IMDB_ID, imdbID)
        findNavController().navigate(R.id.action_mainFragment_to_descriptionFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
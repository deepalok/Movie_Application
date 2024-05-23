package com.aydee.movieapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aydee.movieapplication.databinding.FragmentRegistrationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Fill all details!!!", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, password)
            }
        }

        binding.txtLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    // register using email and password
    private fun registerUser(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Registration success!!!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Registration failed!!!", Toast.LENGTH_SHORT)
                        .show()
                }
                findNavController().popBackStack()
            }
    }

    // release resources
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.aydee.movieapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onDestroy() {
        super.onDestroy()
        // sign out user as soon as app is closed
        Firebase.auth.signOut()
    }
}
package com.j4yesh.tictoetoemultiplayer.ui.Auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.j4yesh.tictoetoemultiplayer.R

class AuthActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)  // Use a container layout for fragments

        // Load LoginFragment when the activity starts
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.authContainer, LoginFragment()) // Replace with your container ID
                .commit()
        }
    }
}
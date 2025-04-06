package com.j4yesh.tictoetoemultiplayer.ui.Register

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.j4yesh.tictoetoemultiplayer.R
import com.j4yesh.tictoetoemultiplayer.ui.home.HomeFragment

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.authContainer, RegisterFragment())
                .commit()
        }
    }
}
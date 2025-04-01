package com.j4yesh.tictoetoemultiplayer.ui.Auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.j4yesh.tictoetoemultiplayer.R


class AuthActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "inside auth activity",Toast.LENGTH_LONG).show()
        setContentView(R.layout.activity_main)
    }
}
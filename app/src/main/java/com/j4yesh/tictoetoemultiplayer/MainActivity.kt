package com.j4yesh.tictoetoemultiplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.j4yesh.tictoetoemultiplayer.Data.UserPreferences
import com.j4yesh.tictoetoemultiplayer.ui.Auth.AuthActivity
import com.j4yesh.tictoetoemultiplayer.ui.Auth.LoginFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val MSG = "com.codewithj4yesh.multiscreen.msg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val userPreferences= UserPreferences(this)

        userPreferences.authToken.asLiveData().observe(this, Observer{
            Toast.makeText(this,it?:"Token is Null",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AuthActivity::class.java))
        })

//        val nextBtn = findViewById<Button>(R.id.login_btn)
//        nextBtn.setOnClickListener {
//            val intent = Intent(this, LoginFragment::class.java)
//            startActivity(intent)
//
//            setContentView(R.layout.activity_main)
//
//            finish()
//            startActivity(Intent(this, AuthActivity::class.java))
//        }
    }
}

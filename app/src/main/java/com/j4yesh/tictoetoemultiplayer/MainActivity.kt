package com.j4yesh.tictoetoemultiplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

        val nextBtn = findViewById<Button>(R.id.login_btn)
        nextBtn.setOnClickListener {
//            val intent = Intent(this, LoginFragment::class.java)
//            startActivity(intent)

            setContentView(R.layout.activity_main)

            finish()
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
}

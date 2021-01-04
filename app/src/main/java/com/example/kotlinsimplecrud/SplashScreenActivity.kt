package com.example.kotlinsimplecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val timeOut : Long = 2000 //2 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Handler().postDelayed({
            if (user != null){
                val dashboardIntent = Intent(this, MainActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            }else{
                val signInIntent = Intent(this, SignInActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        }, timeOut)
    }
}
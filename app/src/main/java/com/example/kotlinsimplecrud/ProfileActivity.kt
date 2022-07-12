package com.example.kotlinsimplecrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val id_txt = findViewById<TextView>(R.id.id_txt)
        val name_txt = findViewById<TextView>(R.id.name_txt)
        val email_txt = findViewById<TextView>(R.id.email_txt)
        val profile_image = findViewById<ImageView>(R.id.profile_image)
        val sign_out_btn = findViewById<Button>(R.id.sign_in_btn)

        id_txt.text = currentUser?.uid
        name_txt.text = currentUser?.displayName
        email_txt.text = currentUser?.email

        Glide.with(this).load(currentUser?.photoUrl).into(profile_image)

        sign_out_btn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
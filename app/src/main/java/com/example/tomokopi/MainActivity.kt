package com.example.tomokopi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    lateinit var _btnLogout: Button
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        _btnLogout = findViewById(R.id.btnLogout)
        _btnLogout.setOnClickListener{
            Firebase.auth.signOut()
            var intent = Intent(this, dashboard:: class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            var intent = Intent(this, dashboard:: class.java)
            startActivity(intent)
        }
    }

}
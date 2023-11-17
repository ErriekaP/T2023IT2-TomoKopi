package com.example.tomokopi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var _btnRegister: Button
    lateinit var _etEmail: EditText
    lateinit var _etPassw: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        _etEmail = findViewById(R.id.etRegEmail)
        _etPassw = findViewById(R.id.etRegPassw)

        _btnRegister = findViewById(R.id.btnRegister)
        _btnRegister.setOnClickListener{

            var val_email = _etEmail.text.toString()
            var val_passw = _etPassw.text.toString()

            var TAG = "ZZZTagFirebase"

            mAuth.createUserWithEmailAndPassword(val_email, val_passw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }

        }
    }

    fun updateUI(user: FirebaseUser?){

        if(user != null){
            var intent = Intent(this, MainActivity:: class.java)
            startActivity(intent)
        }else {
            Toast.makeText(
                baseContext,
                "You did not login.",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}
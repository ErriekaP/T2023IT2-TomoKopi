package com.example.tomokopi

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tomokopi.services.FirebaseLib
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class dashboard : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth


    lateinit var _btnLogin: Button
    lateinit var _btnRegister:Button

    lateinit var _etEmail: EditText
    lateinit var _etPassw: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mAuth = FirebaseAuth.getInstance()

        _btnLogin = findViewById(R.id.btnLogin)
        _btnRegister = findViewById(R.id.btnRegister)

        _etEmail = findViewById(R.id.etLogEmail)
        _etPassw = findViewById(R.id.etLogPass)

        _btnLogin.setOnClickListener{

            var val_email = _etEmail.text.toString()
            var val_passw = _etPassw.text.toString()
            Log.d("ZZZ","click")

            if (val_email.equals("") || val_passw.equals("")){
                Toast.makeText(this, "Required fields", Toast.LENGTH_SHORT)
            } else{
                signInEmailPass(val_email,val_passw)

            }
            //var intent = Intent(this, MainActivity:: class.java)
            //startActivity(intent)
        }
        _btnRegister.setOnClickListener{
            var intent = Intent(this, RegisterActivity:: class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        _etEmail.setText("")
        _etPassw.setText("")
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    override fun onResume() {
        super.onResume()

        _etEmail.setText("")
        _etPassw.setText("")
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

    fun signInEmailPass(email: String, passw: String){
        var TAG = "ZZZTagFirebase"

        mAuth.signInWithEmailAndPassword(email, passw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication successful.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
//        mAuth.signInWithEmailAndPassword(email, passw).addOnCompleteListener(this, OnCompleteListener <AuthResult> {
//            @Override
//            fun onComplete(result: Task<AuthResult>){
//                Log.d("HERE", "????")
//                if (result.isComplete()){
//                    Log.d(TAG, "Success signin")
//                    Toast.makeText(this,"Success signin", Toast.LENGTH_LONG)
//                } else if(result.isSuccessful){
//                    Log.d(TAG, "Not sucessful")
//                    Toast.makeText(this,"Not signin", Toast.LENGTH_LONG)
//                } else{
//                    Log.d(TAG, "Not sucessful")
//                    Toast.makeText(this,"Not signin", Toast.LENGTH_LONG)
//
//                }
//            }
//
//        })
    }
}
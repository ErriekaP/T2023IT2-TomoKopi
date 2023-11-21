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
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    private val userCollection = "users"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)
        val db = Firebase.firestore

        mAuth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etPassw = findViewById<EditText>(R.id.etRegPassw)
        val etName = findViewById<EditText>(R.id.etName)
        val etphotoUrl = findViewById<EditText>(R.id.etphotoUrl)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener{

            var valemail = etEmail.text.toString()
            var valpassw = etPassw.text.toString()
            var valname = etName.text.toString()
            var valphotoUrl = etphotoUrl.text.toString()

            var TAG = "ZZZTagFirebase"

            mAuth.createUserWithEmailAndPassword(valemail, valpassw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val data = hashMapOf(
                            "email" to valemail,
                            "name" to valname,
                            "photoUrl" to valphotoUrl

                        )

                        db.collection("users").document()
                            .set(data)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    baseContext,
                                    "You have successfully registered!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                val user = mAuth.currentUser
                                updateUI(user)
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    baseContext,
                                    "Your registration failed!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

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
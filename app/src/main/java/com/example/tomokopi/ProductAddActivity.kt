package com.example.tomokopi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.util.Date

class ProductAddActivity : AppCompatActivity() {

    private val menuCollectionName = "items"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        val name = findViewById<EditText>(R.id.productAddTitleET)
        val description = findViewById<EditText>(R.id.productAddDescriptionET)
        val price = findViewById<EditText>(R.id.productAddPriceET)
        val imageUrl = findViewById<EditText>(R.id.productAddImageUrlET)
        val addProductButton = findViewById<Button>(R.id.productAddButton)
        val db = Firebase.firestore
        val addBack = findViewById<Button>(R.id.addBack)

        addBack.setOnClickListener {
            finish()
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }

        addProductButton.setOnClickListener {
            val name = name.text.toString()
            val description = description.text.toString()
            val price = price.text.toString()
            val photoUrl = imageUrl.text.toString()

            if (name != "" && description != "" && price != "" && photoUrl != ""){
                val priceDouble = String.format("%.2f", price.toDouble()).toDouble()

                val data = hashMapOf(
                    "name" to name,
                    "description" to description,
                    "price" to priceDouble,
                    "photoUrl" to arrayListOf(photoUrl),
                    "dateCreated" to Timestamp(Date())
                )

                db.collection(menuCollectionName).document()
                    .set(data)
                    .addOnSuccessListener {
                        Toast.makeText(
                            baseContext,
                            "Added Product Successfully",
                            Toast.LENGTH_SHORT,
                        ).show()
                        finish()
                        val mainActivity = Intent(this, MainActivity::class.java)
                        startActivity(mainActivity)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            baseContext,
                            "Addition of Product Failed",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
            }
        }

    }
}
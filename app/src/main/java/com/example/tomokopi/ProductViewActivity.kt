package com.example.tomokopi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ProductViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)

        val thisIntent: Intent = intent
        val title: String = thisIntent.getStringExtra("name").toString()
        val description: String = thisIntent.getStringExtra("description").toString()
        val imageUrl: String = thisIntent.getStringExtra("photoUrl").toString()
        val price: String = thisIntent.getStringExtra("price").toString()

        val viewProductImage = findViewById<ImageView>(R.id.viewProductImage)
        Glide.with(this).load(imageUrl).into(viewProductImage)

        val viewProductTitle = findViewById<TextView>(R.id.viewProductTitle)
        viewProductTitle.text = title

        val viewProductDescription = findViewById<TextView>(R.id.viewProductDescription)
        viewProductDescription.text = description

        val viewProductPrice = findViewById<TextView>(R.id.viewProductPrice)
        viewProductPrice.text = price

        val backButton = findViewById<Button>(R.id.viewProductBackButton)

        backButton.setOnClickListener {
            finish()
        }
    }
}
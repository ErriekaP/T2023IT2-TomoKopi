package com.example.tomokopi

import android.app.ActionBar
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    lateinit var _btnLogout: Button
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Firebase.firestore
        val itemlist = findViewById<LinearLayout>(R.id.item_list)
        val addButton = findViewById<Button>(R.id.mainAddButton)

        mAuth = FirebaseAuth.getInstance()

        _btnLogout = findViewById(R.id.btnLogout)
        _btnLogout.setOnClickListener{
            Firebase.auth.signOut()
            var intent = Intent(this, dashboard:: class.java)
            startActivity(intent)
        }

        Log.d("ZZZ","message")

        addButton.setOnClickListener {
            finish()
            val productAddActivity = Intent(this, ProductAddActivity::class.java)
            startActivity(productAddActivity)
        }

        db.collection("items")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val description = document.data["description"].toString()
                    val imageUrlTemp = document.data["photoUrl"].toString()
                    val imageUrl = imageUrlTemp.substring(1, imageUrlTemp.length - 2)
                    val priceTemp = document.data["price"].toString()
                    val price = String.format("₱ %.2f", priceTemp.toFloat())
                    val title = document.data["name"].toString()
//                    Log.d("ZZZ", description)
//                    Log.d("ZZZ", imageUrl)
//                    Log.d("ZZZ", priceTemp)
//                    Log.d("ZZZ", price)
//                    Log.d("ZZZ", title)

                    val cardV = CardView(this)
                    val cardVParams = ActionBar.LayoutParams(
                        resources.getDimensionPixelOffset
                            (R.dimen.card_width), ActionBar.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, resources.getDimensionPixelOffset(R.dimen.card_margin_bottom))
                    }
                    cardV.layoutParams = cardVParams
                    cardV.radius = 40f

                    val cardLinearLayout = LinearLayout(this)
                    val cardLinearLayoutParams = ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT
                    )
                    cardLinearLayout.gravity = Gravity.CENTER_HORIZONTAL
                    cardLinearLayout.orientation = LinearLayout.VERTICAL
                    cardLinearLayout.setPadding(
                        resources.getDimensionPixelOffset(R.dimen.cardLinearLayout_padding_x),
                        resources.getDimensionPixelOffset(R.dimen.cardLinearLayout_padding_y),
                        resources.getDimensionPixelOffset(R.dimen.cardLinearLayout_padding_x),
                        resources.getDimensionPixelOffset(R.dimen.cardLinearLayout_padding_y)
                    )
                    cardLinearLayout.layoutParams = cardLinearLayoutParams

                    val imageV = ImageView(this)
                    val imageVParams = LinearLayout.LayoutParams(
                        resources.getDimensionPixelOffset(R.dimen.imageView_width), resources.getDimensionPixelOffset(R.dimen.imageView_height)
                    )
                    imageV.layoutParams = imageVParams


                    Glide.with(this).load(imageUrl).into(imageV)

//                    val cardImg = CardView(this)
//                    cardImg.addView(imageV)
//                    val cardImgParams = ActionBar.LayoutParams(
//                        ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
//                    )
//                    cardImg.layoutParams = cardImgParams
//                    cardImg.radius = 30f


                    val titleTv = TextView(this)
                    titleTv.layoutParams = LinearLayout.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT
                    ).apply{
                        topMargin = resources.getDimensionPixelOffset(R.dimen.textView_title_margin_top)
                    }
                    titleTv.text = (title)
                    titleTv.textSize = 18F
                    titleTv.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    titleTv.setTypeface(null, Typeface.BOLD)


                    val descriptionTv = TextView(this)
                    descriptionTv.layoutParams = LinearLayout.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT
                    ).apply{
                        topMargin = resources.getDimensionPixelOffset(R.dimen.textView_title_margin_top)
                    }
                    descriptionTv.text = (description)
                    descriptionTv.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START


                    val priceViewBtnLinearLayout = LinearLayout(this)
                    val priceViewBtnLinearLayoutParams = LinearLayout.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT
                    ).apply{
                        topMargin = resources.getDimensionPixelOffset(R.dimen.textView_title_margin_top)
                    }
                    priceViewBtnLinearLayout.orientation = LinearLayout.HORIZONTAL
                    priceViewBtnLinearLayout.layoutParams = priceViewBtnLinearLayoutParams


                    val priceTv = TextView(this)
                    val priceTvParams = LinearLayout.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        1F
                    )
                    priceTv.layoutParams = priceTvParams

                    priceTv.text = price
                    priceTv.textSize = 18F
                    priceTv.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                    priceTv.setTypeface(null, Typeface.BOLD)


                    val viewBtnTv = TextView(this)
                    viewBtnTv.layoutParams = LinearLayout.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        1F
                    )
                    viewBtnTv.text = "View"
                    viewBtnTv.textSize = 18F
                    viewBtnTv.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
                    viewBtnTv.setTypeface(null, Typeface.BOLD)

                    viewBtnTv.setOnClickListener {
                        val productViewActivity = Intent(this, ProductViewActivity::class.java)
                        productViewActivity.putExtra("photoUrl", imageUrl)
                        productViewActivity.putExtra("name", title)
                        productViewActivity.putExtra("description", description)
                        productViewActivity.putExtra("price", price)
                        startActivity(productViewActivity)
                    }

                    priceViewBtnLinearLayout.addView(priceTv)
                    priceViewBtnLinearLayout.addView(viewBtnTv)

                    cardLinearLayout.addView(imageV)
                    cardLinearLayout.addView(titleTv)
                    cardLinearLayout.addView(descriptionTv)
                    cardLinearLayout.addView(priceViewBtnLinearLayout)


                    cardV.addView(cardLinearLayout)


                    itemlist.addView(cardV)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("zzzz", "Error getting documents.", exception)
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
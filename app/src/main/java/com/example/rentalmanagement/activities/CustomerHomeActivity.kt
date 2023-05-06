package com.example.rentalmanagement.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.rentalmanagement.R

class CustomerHomeActivity : AppCompatActivity() {
    private lateinit var btnFindData: Button
    private lateinit var btnViewData: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home)

        btnFindData = findViewById(R.id.btnFindData)
        btnViewData = findViewById(R.id.btnViewData)

        btnFindData.setOnClickListener {
            val intent = Intent(this,RentingViewActivity::class.java)
            startActivity(intent)
        }
        btnViewData.setOnClickListener {
            val intent = Intent(this,MyRentalActivity::class.java)
            startActivity(intent)
        }

    }
}
package com.example.signupandsignin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.signupandsignin.R

class DriverWallet : AppCompatActivity() {
    private lateinit var addcrdbutton: Button
    private lateinit var viewcrdbutton2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_wallet)

        addcrdbutton = findViewById(R.id.addcrdbutton)
        viewcrdbutton2 = findViewById(R.id.viewcrdbutton2)


        addcrdbutton.setOnClickListener {
            val intent = Intent(this,AddBankCard::class.java)
            startActivity(intent)
        }

        viewcrdbutton2.setOnClickListener {
            val intent = Intent(this,bankCardList::class.java)
            startActivity(intent)
        }

    }
}
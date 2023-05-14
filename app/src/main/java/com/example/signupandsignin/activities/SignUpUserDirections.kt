package com.example.signupandsignin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.signupandsignin.databinding.ActivitySignUpUserDirectionsBinding

class SignUpUserDirections : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpUserDirectionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpUserDirectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDriver.setOnClickListener {
            intent = Intent(applicationContext, signup_driver::class.java)
            startActivity(intent)
        }
        binding.btnRider.setOnClickListener {
            intent = Intent(applicationContext, signup_rider::class.java)
            startActivity(intent)
        }




    }
}
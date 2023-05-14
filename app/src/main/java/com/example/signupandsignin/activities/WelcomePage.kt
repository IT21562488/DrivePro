package com.example.signupandsignin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.signupandsignin.R

class WelcomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        var signIn=findViewById<Button>(R.id.login)
        var signUn=findViewById<Button>(R.id.signup)

        signIn.setOnClickListener {
            var intent=Intent(this, signInActivity::class.java)
            startActivity(intent)
        }

        signUn.setOnClickListener {
            var intent=Intent(this, SignUpUserDirections::class.java)
            startActivity(intent)
        }

    }
}
package com.example.signupandsignin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.signupandsignin.models.UsersModel
import com.example.signupandsignin.databinding.ActivitySignupDriverBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class signup_driver : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivitySignupDriverBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val auth = Firebase.auth
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        binding.register.setOnClickListener {
            val Email = binding.etEmail.text.toString()
            val Password = binding.etPassword.text.toString()
            val userEmail = binding.etEmail.text.toString()
            val userMobile = binding.etPhone.text.toString()
            val name = binding.etName.text.toString()

            if (Email.isNotEmpty() && Password.isNotEmpty() && userEmail.isNotEmpty()  && userMobile.isNotEmpty() && name.isNotEmpty() && (userMobile.length==10)  && (Password.length>5)) {

                firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this) {task->

                    if (task.isSuccessful) {
                        Toast.makeText(this,"SIGN UP SUCCESS", Toast.LENGTH_LONG).show()
                        val auth1=auth.currentUser
                        var userId=auth1?.uid

                        val dataInsert= UsersModel(userId,name,userMobile,userEmail,"driver")
                        dbRef.child(userId!!).setValue(dataInsert).addOnSuccessListener {
                            Toast.makeText(this,"Driver Added", Toast.LENGTH_LONG).show()
                            intent = Intent(applicationContext, signInActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener {err->
                            Toast.makeText(this,"Driver not Added ${err}", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        Toast.makeText(this, "Login fail", Toast.LENGTH_LONG).show()
                    }

                }


            } else {
                if (Email.isEmpty()){
                    Toast.makeText(this,"Please fill Email", Toast.LENGTH_LONG).show()
                }
                if (Password.isEmpty()){
                    Toast.makeText(this,"Please fill password", Toast.LENGTH_LONG).show()
                }
                if(userEmail.isEmpty()){
                    Toast.makeText(this,"Please fill Email", Toast.LENGTH_LONG).show()
                }
                if(userMobile.isEmpty()){
                    Toast.makeText(this,"Please fill Mobile number", Toast.LENGTH_LONG).show()
                }
                if((userMobile.length==10)) {
                }
                else{
                    Toast.makeText(this,"Please enter valid mobile number", Toast.LENGTH_LONG).show()
                }
                if( Password.length<6){
                    Toast.makeText(this,"password length max for 6", Toast.LENGTH_LONG).show()
                }
            }


        }
    }
}
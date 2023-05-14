package com.example.signupandsignin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.signupandsignin.models.UsersModel
import com.example.signupandsignin.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class signInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")


        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                        databaseReference.child(firebaseAuth.currentUser!!.uid).addValueEventListener(object :
                            ValueEventListener {

                            override fun onDataChange(snapshot: DataSnapshot) {
                                //retrieve values from the db and convert them to user data class
                                var user = snapshot.getValue(UsersModel::class.java)!!

                                if( user.UserType == "driver") {
                                    intent = Intent(applicationContext, UserHomeActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    intent = Intent(applicationContext, CustomerHomeActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {

                            }
                    })
                    }else {
                        Toast.makeText(
                            this,
                            "Login Fail" + it.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
               if(email.isEmpty()){
                   Toast.makeText(this,"Email field is Empty",Toast.LENGTH_LONG).show()
               }
                if(pass.isEmpty()){
                    Toast.makeText(this,"Password field is Empty",Toast.LENGTH_LONG).show()
                }
            }

        }

    }
}
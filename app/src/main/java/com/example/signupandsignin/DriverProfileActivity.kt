package com.example.signupandsignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.signupandsignin.databinding.ActivityDriverProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class DriverProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDriverProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var user : UsersModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.child(firebaseAuth.currentUser!!.uid).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                user = snapshot.getValue(UsersModel::class.java)!!

                binding.etFullName.text = user.UserName
                binding.etEmail.text = user.UserEmail
                binding.etContactNumber.text = user.UserMobile
                binding.duserName.text = user.UserName

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

        binding.btnUpdate.setOnClickListener {
            intent = Intent(applicationContext, DriverProfileUpdateActivity::class.java).also {
                it.putExtra("name", user.UserName)
                it.putExtra("email", user.UserEmail)
                it.putExtra("mobile", user.UserMobile)
                startActivity(it)
            }


        }

        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()

            //redirect user to login page
            intent = Intent(applicationContext, WelcomePage::class.java)
            startActivity(intent)

            //toast message
            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()
        }
    }

}
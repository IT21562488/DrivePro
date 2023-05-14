package com.example.signupandsignin.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.signupandsignin.databinding.ActivityDriverProfileUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DriverProfileUpdateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDriverProfileUpdateBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverProfileUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        var uid = auth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        //fetch data from the intent
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val mobile = intent.getStringExtra("mobile")
        val vcount = intent.getStringExtra("vcount")

        //bind valuees to editTexts
        binding.etName.setText(name)
        binding.etEmail.setText(email)
        binding.etContactNumber.setText(mobile)
        binding.etVCountunt.setText(vcount)



        binding.btnConfirm.setOnClickListener {
            var etName = binding.etName.text.toString()
            var etEmail = binding.etEmail.text.toString()
            var etConatctNo = binding.etContactNumber.text.toString()
            var etVCountunt = binding.etVCountunt.text.toString()

            if (etName.isNotEmpty() && etEmail.isNotEmpty()  && etConatctNo.isNotEmpty() && etVCountunt.isNotEmpty()){

                val map = HashMap<String,Any>()
                //add data to hashMap
                map["userName"] = etName
                map["userEmail"] = etEmail
                map["userMobile"] = etConatctNo
                map["vehicleCount"] = etVCountunt



                //update database from hashMap
                databaseReference.child(uid).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, DriverProfileActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }


            } else {
                if (etName.isEmpty()){
                    Toast.makeText(this,"Please fill Name", Toast.LENGTH_LONG).show()
                }
                if (etEmail.isEmpty()){
                    Toast.makeText(this,"Please fill Email", Toast.LENGTH_LONG).show()
                }
                if(etConatctNo.isEmpty()){
                    Toast.makeText(this,"Please fill Contact No", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnDlt.setOnClickListener {
            Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show()
            //delete account
            var currUser = auth.currentUser
            currUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //delete user data entry from db
                        databaseReference.child(uid).removeValue().addOnCompleteListener {
                            if( it.isSuccessful){
                                Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show()
                                intent = Intent(applicationContext, WelcomePage::class.java)
                                startActivity(intent)

                            }
                        }
                    } else {
                        Toast.makeText(this, "Failed to delete the account", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }
}
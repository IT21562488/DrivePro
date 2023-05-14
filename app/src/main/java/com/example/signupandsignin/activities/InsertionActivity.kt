package com.example.signupandsignin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.signupandsignin.R
import com.example.signupandsignin.databinding.ActivityDriverProfileBinding
import com.example.signupandsignin.databinding.ActivityInsertionBinding
import com.example.signupandsignin.models.UsersModel
import com.example.signupandsignin.models.VehicleModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class InsertionActivity : AppCompatActivity() {

    private lateinit var customerID: TextView
    private lateinit var vehiclemodel: EditText
    private lateinit var transition: EditText
    private lateinit var passengers: EditText
    private lateinit var availbledays: EditText
    private lateinit var price: EditText
    private lateinit var location: EditText
    private lateinit var btnSaveData: Button

    private lateinit var binding: ActivityInsertionBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user : UsersModel

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_insertion)
        binding = ActivityInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.child(firebaseAuth.currentUser!!.uid).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                user = snapshot.getValue(UsersModel::class.java)!!
                binding.CusID.text = user.UserId

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

        customerID=findViewById(R.id.CusID)
        vehiclemodel=findViewById(R.id.vehiclemodel)
        transition=findViewById(R.id.transition)
        passengers=findViewById(R.id.passengers)
        availbledays=findViewById(R.id.availbledays)
        price=findViewById(R.id.price)
        location=findViewById(R.id.location)
        btnSaveData=findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Vehicle")

        btnSaveData.setOnClickListener {
            saveVehicleData()
        }


    }

    private fun saveVehicleData() {
        val cusID = customerID.text.toString()
        val vehimodel = vehiclemodel.text.toString()
        val trans = transition.text.toString()
        val passen = passengers.text.toString()
        val available = availbledays.text.toString()
        val prices = price.text.toString()
        val locations = location.text.toString()

    if(vehimodel.isEmpty()||trans.isEmpty()||passen.isEmpty()||available.isEmpty()||prices.isEmpty()||locations.isEmpty()||!passen.matches("[1-5]".toRegex())) {
        if (vehimodel.isEmpty()) {
            vehiclemodel.error = "Please enter Vehicle model"
        }
        if (trans.isEmpty()) {
            transition.error = "Please enter Transition type"
        }
        if (passen.isEmpty()) {
            passengers.error = "Please enter passengers"

        }else if (!passen.matches("[1-5]".toRegex())) {
            passengers.error = "maximum passengers must be five"
        }

        if (available.isEmpty()) {
            availbledays.error = "Please enter available"
        }
        if (prices.isEmpty()) {
            price.error = "Please enter prices"
        }
        if (locations.isEmpty()) {
            location.error = "Please enter location"
        }
    }
    else {
        val vehicleID = dbRef.push().key!!

        val vehicle =
            VehicleModel(vehicleID,cusID, vehimodel, trans, passen, available, prices, locations)

        dbRef.child(vehicleID).setValue(vehicle)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

}
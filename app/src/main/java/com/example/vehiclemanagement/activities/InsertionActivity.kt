package com.example.vehiclemanagement.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.vehiclemanagement.R
import com.example.vehiclemanagement.models.VehicleModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var customerID: TextView
    private lateinit var vehiclemodel: EditText
    private lateinit var transition: EditText
    private lateinit var passengers: EditText
    private lateinit var availbledays: EditText
    private lateinit var price: EditText
    private lateinit var location: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)



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

        if (vehimodel.isEmpty()) {
            vehiclemodel.error = "Please enter name"
        }
        if (trans.isEmpty()) {
            transition.error = "Please enter name"
        }
        if (passen.isEmpty()) {
            passengers.error = "Please enter name"
        }
        if (available.isEmpty()) {
            availbledays.error = "Please enter name"
        }
        if (prices.isEmpty()) {
            price.error = "Please enter name"
        }
        if (locations.isEmpty()) {
            location.error = "Please enter name"
        }
        val vehicleID = dbRef.push().key!!

        val vehicle = VehicleModel(vehicleID,cusID,vehimodel,trans,passen,available,prices,locations)

        dbRef.child(vehicleID).setValue(vehicle)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}
package com.example.rentalmanagement.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.rentalmanagement.R
import com.example.rentalmanagement.models.RentalModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RentingInserActivity : AppCompatActivity() {

    private lateinit var rtvvehicleid: TextView
    private lateinit var rtvmodel: TextView
    private lateinit var startTrip: EditText
    private lateinit var startTime: EditText
    private lateinit var endTrip: EditText
    private lateinit var endTime: EditText
    private lateinit var rPassengers: EditText
    private lateinit var rLocation: EditText
    private lateinit var btnbooknow: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renting_inser)

        initView()
        setValuesToViews()

        rtvvehicleid = findViewById(R.id.rtvvehicleid)
        rtvmodel = findViewById(R.id.rtvmodel)
        startTrip = findViewById(R.id.startTrip)
        startTime = findViewById(R.id.startTime)
        endTrip = findViewById(R.id.endTrip)
        endTime = findViewById(R.id.endTime)
        rPassengers = findViewById(R.id.rPassengers)
        rLocation = findViewById(R.id.rLocation)
        btnbooknow=findViewById(R.id.btnbooknow)

        dbRef = FirebaseDatabase.getInstance().getReference("Rentals")

        btnbooknow.setOnClickListener {
            saveRentalData()
        }



    }

    private fun saveRentalData() {

        val vehiID = rtvvehicleid.text.toString()
        val vehimodel = rtvmodel.text.toString()
        val sTrip = startTrip.text.toString()
        val sTime = startTime.text.toString()
        val eTrip = endTrip.text.toString()
        val eTime = endTime.text.toString()
        val rpassen = rPassengers.text.toString()
        val rlocations = rLocation.text.toString()


        if (sTrip.isEmpty()) {
            startTrip.error = "Please enter name"
        }
        if (sTime.isEmpty()) {
            startTime.error = "Please enter name"
        }
        if (eTrip.isEmpty()) {
            endTrip.error = "Please enter name"
        }
        if (eTime.isEmpty()) {
            endTime.error = "Please enter name"
        }
        if (rpassen.isEmpty()) {
            rPassengers.error = "Please enter name"
        }
        if (rlocations.isEmpty()) {
            rLocation.error = "Please enter name"
        }
        val rentalID = dbRef.push().key!!

        val rental = RentalModel(rentalID,vehimodel,vehiID,sTrip,sTime,eTrip,eTime,rpassen,rlocations)

        dbRef.child(rentalID).setValue(rental)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }


    private fun setValuesToViews() {

        rtvvehicleid.text = intent.getStringExtra("vehicleID")
        rtvmodel.text=intent.getStringExtra("vehimodel")
    }

    private fun initView() {

        rtvvehicleid = findViewById(R.id.rtvvehicleid)
        rtvmodel = findViewById(R.id.rtvmodel)
    }
}
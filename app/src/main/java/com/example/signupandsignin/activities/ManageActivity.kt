package com.example.signupandsignin.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.signupandsignin.R
import com.example.signupandsignin.models.RentalModel

import com.google.firebase.database.FirebaseDatabase

class ManageActivity : AppCompatActivity() {
    private lateinit var tvRID: TextView
    private lateinit var tvRVID: TextView
    private lateinit var tvStrip: TextView
    private lateinit var tvstime: TextView
    private lateinit var tvEtrip: TextView
    private lateinit var tvEtime: TextView
    private lateinit var tvRMPassen: TextView
    private lateinit var tvRMlocation: TextView
    private lateinit var btnRUpdate: Button
    private lateinit var btnRDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        initView()
        setValuesToViews()

        btnRUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("rentalID").toString(),
                intent.getStringExtra("sTrip").toString()
            )
        }
        btnRDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("rentalID").toString()
            )
        }
    }

    private fun deleteRecord(rentalID: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("Rentals").child(rentalID)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Rental data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MyRentalActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(rentalID: String, sTrip: String) {

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_rental, null)

        mDialog.setView(mDialogView)


        val retvid = mDialogView.findViewById<TextView>(R.id.retvid)
        val etstartTrip = mDialogView.findViewById<EditText>(R.id.etstartTrip)
        val etstartTime = mDialogView.findViewById<EditText>(R.id.etstartTime)
        val etendTrip = mDialogView.findViewById<EditText>(R.id.etendTrip)
        val etendTime = mDialogView.findViewById<EditText>(R.id.etendTime)
        val etrPassengers = mDialogView.findViewById<EditText>(R.id.etrPassengers)
        val etrLocation = mDialogView.findViewById<EditText>(R.id.etrLocation)

        val rentUpdate = mDialogView.findViewById<Button>(R.id.rentUpdate)


        retvid.setText(intent.getStringExtra("vehiID").toString())
        etstartTrip.setText(intent.getStringExtra("sTrip").toString())
        etstartTime.setText(intent.getStringExtra("sTime").toString())
        etendTrip.setText(intent.getStringExtra("eTrip").toString())
        etendTime.setText(intent.getStringExtra("eTime").toString())
        etrPassengers.setText(intent.getStringExtra("rPassen").toString())
        etrLocation.setText(intent.getStringExtra("rlocations").toString())

        mDialog.setTitle("Updating $sTrip Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        rentUpdate.setOnClickListener {
            updateVehicleData(
                rentalID,
                retvid.text.toString(),
                etstartTrip.text.toString(),
                etstartTime.text.toString(),
                etendTrip.text.toString(),
                etendTime.text.toString(),
                etrPassengers.text.toString(),
                etrLocation.text.toString(),

            )

            Toast.makeText(applicationContext, "Rental Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews

            tvRVID.text=etstartTrip.text.toString()
            tvStrip.text=etstartTrip.text.toString()
            tvstime.text=etstartTime.text.toString()
            tvEtrip.text=etendTrip.text.toString()
            tvEtime.text=etendTime.text.toString()
            tvRMPassen.text=etrLocation.text.toString()
            tvRMlocation.text=etrPassengers.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateVehicleData(
        rentalID: String,
        vehiID:String,
        Strip: String,
        Stime: String,
        Etrip: String,
        Etime: String,
        passen: String,
        location: String
    ) {


        val dbRef = FirebaseDatabase.getInstance().getReference("Rentals").child(rentalID)
        val empInfo = RentalModel(rentalID,vehiID,Strip,Stime,Etrip,Etime, passen,location)
        dbRef.setValue(empInfo)
    }

    private fun setValuesToViews() {

        tvRID.text = intent.getStringExtra("rentalID")
        tvRVID.text=intent.getStringExtra("vehiID")
        tvStrip.text=intent.getStringExtra("sTrip")
        tvstime.text=intent.getStringExtra("sTime")
        tvEtrip.text=intent.getStringExtra("eTrip")
        tvEtime.text=intent.getStringExtra("eTime")
        tvRMPassen.text=intent.getStringExtra("rPassen")
        tvRMlocation.text=intent.getStringExtra("rlocations")
    }

    private fun initView() {

        tvRID = findViewById(R.id.tvRID)
        tvRVID = findViewById(R.id.tvRVID)
        tvStrip = findViewById(R.id.tvStrip)
        tvstime = findViewById(R.id.tvstime)
        tvEtrip = findViewById(R.id.tvEtrip)
        tvEtime = findViewById(R.id.tvEtime)
        tvRMPassen = findViewById(R.id.tvRMPassen)
        tvRMlocation = findViewById(R.id.tvRMlocation)
        btnRUpdate = findViewById(R.id.btnRUpdate)
        btnRDelete = findViewById(R.id.btnRDelete)
    }
}
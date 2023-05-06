package com.example.vehiclemanagement.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.vehiclemanagement.R
import com.example.vehiclemanagement.models.VehicleModel
import com.google.firebase.database.FirebaseDatabase

class TestActivity : AppCompatActivity() {

    private lateinit var tvDID: TextView
    private lateinit var tvID: TextView
    private lateinit var tvmodel: TextView
    private lateinit var tvlocation: TextView
    private lateinit var tvprice: TextView
    private lateinit var tvtrans: TextView
    private lateinit var tvavaible: TextView
    private lateinit var tvpas: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("vehicleID").toString(),
                intent.getStringExtra("vehimodel").toString()
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("vehicleID").toString()
            )
        }
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Vehicle").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Vehicle data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvDID = findViewById(R.id.tvDID)
        tvID = findViewById(R.id.tvID)
        tvmodel = findViewById(R.id.tvmodel)
        tvlocation = findViewById(R.id.tvlocation)
        tvprice = findViewById(R.id.tvprice)
        tvtrans = findViewById(R.id.tvtrans)
        tvavaible = findViewById(R.id.tvavaible)
        tvpas = findViewById(R.id.tvpas)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvDID.text = intent.getStringExtra("cusID")
        tvID.text = intent.getStringExtra("vehicleID")
        tvmodel.text=intent.getStringExtra("vehimodel")
        tvlocation.text=intent.getStringExtra("locations")
        tvprice.text=intent.getStringExtra("prices")
        tvtrans.text=intent.getStringExtra("trans")
        tvavaible.text=intent.getStringExtra("available")
        tvpas.text=intent.getStringExtra("passen")

    }
    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        vehicleID: String,
        vehimodel: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_vehicle, null)

        mDialog.setView(mDialogView)

        val etdriveID = mDialogView.findViewById<TextView>(R.id.etdriveID)
        val etVehicleModel = mDialogView.findViewById<EditText>(R.id.etVehicleModel)
        val etlocation = mDialogView.findViewById<EditText>(R.id.etlocation)
        val etprice = mDialogView.findViewById<EditText>(R.id.etprice)
        val ettrans = mDialogView.findViewById<EditText>(R.id.ettrans)
        val etpas = mDialogView.findViewById<EditText>(R.id.etpas)
        val etavailable = mDialogView.findViewById<EditText>(R.id.etavailable)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etdriveID.setText(intent.getStringExtra("cusID").toString())
        etVehicleModel.setText(intent.getStringExtra("vehimodel").toString())
        etlocation.setText(intent.getStringExtra("locations").toString())
        etprice.setText(intent.getStringExtra("prices").toString())
        ettrans.setText(intent.getStringExtra("trans").toString())
        etpas.setText(intent.getStringExtra("passen").toString())
        etavailable.setText(intent.getStringExtra("available").toString())

        mDialog.setTitle("Updating $vehimodel Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateVehicleData(
                vehicleID,
                etdriveID.text.toString(),
                etVehicleModel.text.toString(),
                etlocation.text.toString(),
                etprice.text.toString(),
                ettrans.text.toString(),
                etpas.text.toString(),
                etavailable.text.toString()

            )

            Toast.makeText(applicationContext, "Vehicle Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvDID.text=etdriveID.text.toString()
            tvmodel.text=etVehicleModel.text.toString()
            tvlocation.text=etlocation.text.toString()
            tvprice.text=etprice.text.toString()
            tvtrans.text=ettrans.text.toString()
            tvavaible.text=etavailable.text.toString()
            tvpas.text=etpas.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateVehicleData(
        id: String,
        did:String,
        model: String,
        location: String,
        price: String,
        trans: String,
        pas: String,
        available: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Vehicle").child(id)
        val empInfo = VehicleModel(id,did, model,trans, pas, available,price,location)
        dbRef.setValue(empInfo)
    }
}
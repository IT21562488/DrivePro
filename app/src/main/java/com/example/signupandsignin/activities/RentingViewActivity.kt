package com.example.signupandsignin.activities


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signupandsignin.adapters.VehicleRentingAdapter
import com.example.signupandsignin.models.VehicleModel

import com.google.firebase.database.*

class RentingViewActivity : AppCompatActivity() {

    private lateinit var rentingVehicleRecyclerView: RecyclerView
    private lateinit var tvRLoadingData: TextView
    private lateinit var vehicleList: ArrayList<VehicleModel>
    private lateinit var dbRef: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.signupandsignin.R.layout.activity_renting_view)

        rentingVehicleRecyclerView = findViewById(com.example.signupandsignin.R.id.rvRvehicle)
        rentingVehicleRecyclerView.layoutManager = LinearLayoutManager(this)
        rentingVehicleRecyclerView.setHasFixedSize(true)
        tvRLoadingData = findViewById(com.example.signupandsignin.R.id.tvRLoadingData)

        vehicleList= arrayListOf<VehicleModel>()

        getVehicleData()
    }

    private fun getVehicleData() {
        rentingVehicleRecyclerView.visibility = View.GONE
        tvRLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Vehicle")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                vehicleList.clear()
                if (snapshot.exists()){
                    for (vehicleSnap in snapshot.children){
                        val vehicleData = vehicleSnap.getValue(VehicleModel::class.java)
                        vehicleList.add(vehicleData!!)
                    }
                    val mAdapter = VehicleRentingAdapter(vehicleList)
                    rentingVehicleRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : VehicleRentingAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@RentingViewActivity,RentingInserActivity::class.java)

                            //put extras
                            intent.putExtra("vehicleID", vehicleList[position].vehicleID)
                            intent.putExtra("vehimodel", vehicleList[position].vehimodel)
                            intent.putExtra("trans", vehicleList[position].trans)
                            intent.putExtra("passen", vehicleList[position].passen)
                            intent.putExtra("available", vehicleList[position].available)
                            intent.putExtra("prices", vehicleList[position].prices)
                            intent.putExtra("locations", vehicleList[position].locations)

                            startActivity(intent)
                        }

                    })

                    rentingVehicleRecyclerView.visibility = View.VISIBLE
                    tvRLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}
package com.example.vehiclemanagement.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclemanagement.R
import com.example.vehiclemanagement.adapters.VehicleAdapter
import com.example.vehiclemanagement.models.VehicleModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var vehicleRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var vehicleList: ArrayList<VehicleModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        vehicleRecyclerView = findViewById(R.id.rvvehicle)
        vehicleRecyclerView.layoutManager = LinearLayoutManager(this)
        vehicleRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        vehicleList= arrayListOf<VehicleModel>()

        getVehicleData()
    }

    private fun getVehicleData() {
        vehicleRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Vehicle")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                vehicleList.clear()
                if (snapshot.exists()){
                    for (vehicleSnap in snapshot.children){
                        val vehicleData = vehicleSnap.getValue(VehicleModel::class.java)
                        vehicleList.add(vehicleData!!)
                    }
                    val mAdapter = VehicleAdapter(vehicleList)
                    vehicleRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : VehicleAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity,TestActivity::class.java)

                            //put extras
                            intent.putExtra("vehicleID", vehicleList[position].vehicleID)
                            intent.putExtra("cusID", vehicleList[position].cusID)
                            intent.putExtra("vehimodel", vehicleList[position].vehimodel)
                            intent.putExtra("trans", vehicleList[position].trans)
                            intent.putExtra("passen", vehicleList[position].passen)
                            intent.putExtra("available", vehicleList[position].available)
                            intent.putExtra("prices", vehicleList[position].prices)
                            intent.putExtra("locations", vehicleList[position].locations)

                            startActivity(intent)
                        }

                    })

                    vehicleRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}
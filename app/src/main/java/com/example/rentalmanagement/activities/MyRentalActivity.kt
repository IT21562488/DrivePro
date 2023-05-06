package com.example.rentalmanagement.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalmanagement.R
import com.example.rentalmanagement.adapters.MyRentalAdapter
import com.example.rentalmanagement.adapters.VehicleRentingAdapter
import com.example.rentalmanagement.models.RentalModel
import com.example.rentalmanagement.models.VehicleModel
import com.google.firebase.database.*

class MyRentalActivity : AppCompatActivity() {

    private lateinit var myRentalRecyclerView: RecyclerView
    private lateinit var tvMyRLoadingData: TextView
    private lateinit var rentalList: ArrayList<RentalModel>
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_rental)

        myRentalRecyclerView = findViewById(R.id.rvmyRvehicle)
        myRentalRecyclerView.layoutManager = LinearLayoutManager(this)
        myRentalRecyclerView.setHasFixedSize(true)
        tvMyRLoadingData = findViewById(R.id.tvmyRLoadingData)

        rentalList= arrayListOf<RentalModel>()

        getRentalData()
    }

    private fun getRentalData() {

        myRentalRecyclerView.visibility = View.GONE
        tvMyRLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Rentals")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                rentalList.clear()
                if (snapshot.exists()){
                    for (vehicleSnap in snapshot.children){
                        val rentalData = vehicleSnap.getValue(RentalModel::class.java)
                        rentalList.add(rentalData!!)
                    }
                    val mAdapter = MyRentalAdapter(rentalList)
                    myRentalRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : MyRentalAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@MyRentalActivity,ManageActivity::class.java)

                            //put extras
                            intent.putExtra("rentalID", rentalList[position].rentalID)
                            intent.putExtra("vehimodel", rentalList[position].vehimodel)
                            intent.putExtra("vehiID", rentalList[position].vehiID)
                            intent.putExtra("sTrip", rentalList[position].sTrip)
                            intent.putExtra("sTime", rentalList[position].sTime)
                            intent.putExtra("eTrip", rentalList[position].eTrip)
                            intent.putExtra("eTime", rentalList[position].eTime)
                            intent.putExtra("rPassen", rentalList[position].rPassen)
                            intent.putExtra("rlocations", rentalList[position].rlocations)

                            startActivity(intent)
                        }

                    })

                    myRentalRecyclerView.visibility = View.VISIBLE
                    tvMyRLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
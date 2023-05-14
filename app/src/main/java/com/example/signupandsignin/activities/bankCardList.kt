package com.example.signupandsignin.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signupandsignin.R
import com.example.signupandsignin.adapters.BankCrdAdapter
import com.example.signupandsignin.models.BankCradModel
import com.example.signupandsignin.models.VehicleModel
import com.example.vehiclemanagement.adapters.VehicleAdapter
import com.google.firebase.database.*

class bankCardList : AppCompatActivity() {

    private lateinit var crdRecyclerView: RecyclerView
    private lateinit var tvcrdLoadingData: TextView
    private lateinit var cardList: ArrayList<BankCradModel>
    private lateinit var dbRef: DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_card_list)

        crdRecyclerView = findViewById(R.id.rvcrd)
        crdRecyclerView.layoutManager = LinearLayoutManager(this)
        crdRecyclerView.setHasFixedSize(true)
        tvcrdLoadingData = findViewById(R.id.tvcrdRLoadingData)

        cardList= arrayListOf<BankCradModel>()

        getVehicleData()
    }

    private fun getVehicleData() {
        crdRecyclerView.visibility = View.GONE
        tvcrdLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("BankCards")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cardList.clear()
                if (snapshot.exists()){
                    for (crdSnap in snapshot.children){
                        val vehicleData = crdSnap.getValue(BankCradModel::class.java)
                        cardList.add(vehicleData!!)
                    }
                    val mAdapter = BankCrdAdapter(cardList)
                    crdRecyclerView.adapter = mAdapter
//
//                    mAdapter.setOnItemClickListener(object : BankCrdAdapter.onItemClickListener{
//                        override fun onItemClick(position: Int) {
//
//                            val intent = Intent(this@bankCardList,BankCrdView::class.java)
//
//                            //put extras
//                            intent.putExtra("CardID", cardList[position].CardID)
//                            intent.putExtra("bank", cardList[position].bank)
//                            intent.putExtra("city", cardList[position].city)
//                            intent.putExtra("crdNo", cardList[position].crdNo)
//                            intent.putExtra("hname", cardList[position].hname)
//
//
//                            startActivity(intent)
//                        }
//
//                    })

                    crdRecyclerView.visibility = View.VISIBLE
                    tvcrdLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}
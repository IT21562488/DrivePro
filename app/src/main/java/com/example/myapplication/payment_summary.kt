package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private val db = Firebase.firestore


class payment_summary : AppCompatActivity() {

    private lateinit var cradHolderName: TextView
    private lateinit var cradNo: TextView
    private lateinit var payDate: TextView
    private lateinit var amount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_summary)


        val stringExtra = intent.getStringExtra("DocID")

        cradHolderName = findViewById(R.id.holdName)
        cradNo = findViewById(R.id.cardNo)
        payDate = findViewById(R.id.payDate)
        amount = findViewById(R.id.amount)

        db.collection("Conform Oder")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                   // Toast.makeText(this, stringExtra,Toast.LENGTH_LONG).show()
                   // Log.d(TAG, "${document.id} => ${document.data}")
                    if(document.id == stringExtra){
                        val cHName = document.data.get("Card Holder Name").toString()
                        val cNo = document.data.get("CardNo").toString()
                        val pDay = document.data.get("DaveTime").toString()
                        val pAmount = document.data.get("Amount").toString()
                      //  Log.d(TAG, "${document.id} => ${document.data}")
                        cradHolderName.setText(cHName)
                        cradNo.setText("***"+cNo.subSequence(3.rangeTo(10))+"*****")
                        payDate.setText(pDay)
                        amount.setText(pAmount)

                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


        findViewById<Button>(R.id.btnBack).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,deiver_payment_edit::class.java))
        })
    }
}
package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private lateinit var bankSelect: Spinner
private lateinit var cityName: Spinner
private lateinit var cardNo: EditText
private lateinit var holdName: EditText

private val db = Firebase.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bankSelect = findViewById(R.id.spinnerBank)
        cityName = findViewById(R.id.spinnerCity)
        cardNo = findViewById(R.id.cardNoadd)
        holdName = findViewById(R.id.cardHoldName)

        findViewById<Button>(R.id.btnAddData).setOnClickListener(View.OnClickListener {

            val bName = bankSelect.selectedItem.toString()
            val cName = cityName.selectedItem.toString()
            val cNo = cardNo.text.toString()
            val hName = holdName.text.toString()

            if (!holdName.text.toString().isEmpty() && !cNo.isEmpty()) {
                if (spinnerChack()) {
                    if (spinnerChackCity()) {
                        // Add bank ditalis
                        val details = hashMapOf(
                            "Bank Name" to bName,
                            "City Name" to cName,
                            "Card No" to cNo,
                            "Holder Name" to hName
                        )

// Add a new document with a generated ID
                        db.collection("Add Bank Details")
                            .add(details)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    TAG,
                                    "DocumentSnapshot added with ID: ${documentReference.id}"

                                )
                                startActivity(Intent(this,MainActivity::class.java))
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }

                    }
                }
            } else {
                Toast.makeText(this@MainActivity, "TextField is Empty", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun spinnerChackCity(): Boolean {
        val bank = cityName.selectedItem.toString()
        if (bank == "Select City") {
            Toast.makeText(this@MainActivity, "Select City Name", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }


    private fun spinnerChack(): Boolean {
        val bank = bankSelect.selectedItem.toString()
        if (bank == "Select Bank") {
            Toast.makeText(this@MainActivity, "Select Bank Name", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}

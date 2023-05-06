package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
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
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


private  val db = Firebase.firestore


class deiver_payment_edit : AppCompatActivity() {

    private lateinit var cradHolder : EditText
    private lateinit var cradNo : EditText
    private lateinit var cvvNo : EditText
    private lateinit var exDate : EditText
    private lateinit var amount : TextView


    lateinit var docId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deiver_payment_edit)


        cradHolder = findViewById(R.id.holderName)
        cradNo = findViewById(R.id.curdNumber)
        cvvNo = findViewById(R.id.cvvNo)
        exDate = findViewById(R.id.Exdate)
        amount = findViewById(R.id.amount)
        val current = Calendar.getInstance().time

        val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(current)
        val timeFormat = DateFormat.getTimeInstance().format(current)

        findViewById<Button>(R.id.btnPay).setOnClickListener(View.OnClickListener {
            // Create a new payment with a first and last name
            val user = hashMapOf(
                "Card Holder Name" to cradHolder.text.toString().uppercase(),
                "CardNo" to cradNo.text.toString(),
                "cvvNo" to cvvNo.text.toString(),
                "Ex Date | Year" to exDate.text.toString(),
                "Amount" to amount.text.toString(),
                "DaveTime" to  dateFormat+" "+timeFormat
            )

// Add a new document with a generated ID
            if(!cradHolder.text.toString().isEmpty() && !cradNo.text.toString().isEmpty() && !cvvNo.text.toString().isEmpty() && !exDate.text.toString().isEmpty()){
                db.collection("Conform Oder")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        docId = documentReference.id
                        // Toast.makeText(this, documentReference.id+" "+docId,Toast.LENGTH_LONG).show()
                        Log.d(TAG, " ${docId}")
                        Toast.makeText(this, "Pay Success..!",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,payment_summary::class.java).putExtra("DocID", docId))
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }else{
                Toast.makeText(this, "TextField is Empty",Toast.LENGTH_LONG).show()
            }


        })

        findViewById<Button>(R.id.btnBooking).setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Connect Activity!",Toast.LENGTH_LONG).show()
           startActivity(Intent(this,Edit_payment::class.java))
        })
    }
}
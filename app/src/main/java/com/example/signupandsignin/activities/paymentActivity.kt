package com.example.signupandsignin.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.signupandsignin.R
import com.example.signupandsignin.models.PaymentModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class paymentActivity : AppCompatActivity() {
    private lateinit var crdholderName: EditText
    private lateinit var curdNumber: EditText
    private lateinit var cvv: EditText
    private lateinit var exdate: EditText
    private lateinit var price: TextView
    private lateinit var btnpayData: Button
    private lateinit var  btnBooking: Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


        crdholderName=findViewById(R.id.holderName)
        curdNumber=findViewById(R.id.curdNumber)
        cvv=findViewById(R.id.cvvNo)
        exdate=findViewById(R.id.Exdate)
        price=findViewById(R.id.price)
        btnpayData=findViewById(R.id.btnPay)

        dbRef = FirebaseDatabase.getInstance().getReference("Payment")

        btnpayData.setOnClickListener {
            savePaymentData()
            val intent = Intent(this,RentingViewActivity::class.java)
            startActivity(intent)
        }


//        btnBooking.setOnClickListener {
//            val intent = Intent(this,RentingViewActivity::class.java)
//            startActivity(intent)
//        }

    }

    private fun savePaymentData() {
        val hdname = crdholderName.text.toString()
        val cdnum = curdNumber.text.toString()
        val cvvNO = cvv.text.toString()
        val expdate = exdate.text.toString()
        val payment = price.text.toString()


        if(hdname.isEmpty()||cdnum.isEmpty()||cvvNO.isEmpty()||expdate.isEmpty()||payment.isEmpty()) {
            if (hdname.isEmpty()) {
                crdholderName.error = "Please enter name"
            }
            if (cdnum.isEmpty()) {
                curdNumber.error = "Please enter name"
            }
            if (cvvNO.isEmpty()) {
                cvv.error = "Please enter name"
            }
            if (expdate.isEmpty()) {
                exdate.error = "Please enter name"
            }
            if (payment.isEmpty()) {
                price.error = "Please enter name"
            }

        }
        else {
            val PaymentID = dbRef.push().key!!
            val payment = PaymentModel(PaymentID,hdname,cdnum,cvvNO,expdate,payment)

            dbRef.child(PaymentID).setValue(payment)
                .addOnCompleteListener {
                    Toast.makeText(this, "payment successfully", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
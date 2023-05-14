package com.example.signupandsignin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.signupandsignin.R
import com.example.signupandsignin.models.BankCradModel
import com.example.signupandsignin.models.PaymentModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase





class AddBankCard : AppCompatActivity() {

    private lateinit var spinnerBank: Spinner
    private lateinit var spinnerCity: Spinner
    private lateinit var cardNoadd: EditText
    private lateinit var cardHoldName: EditText

    private lateinit var btnAddData: Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bank_card)

        spinnerBank=findViewById(R.id.spinnerBank)
        spinnerCity=findViewById(R.id.spinnerCity)
        cardNoadd=findViewById(R.id.cardNoadd)
        cardHoldName=findViewById(R.id.cardHoldName)
        btnAddData=findViewById(R.id.btnAddData)

        dbRef = FirebaseDatabase.getInstance().getReference("BankCards")

        btnAddData.setOnClickListener {
            saveCrdData()
            val intent = Intent(this,DriverProfileActivity::class.java)
            startActivity(intent)
        }


    }
    private fun saveCrdData() {
        val bank = spinnerBank.toString()
        val city = spinnerCity.toString()
        val crdNo = cardNoadd.text.toString()
        val hname = cardHoldName.text.toString()


            val CardID = dbRef.push().key!!
            val card = BankCradModel(CardID,bank,city,crdNo,hname)

            dbRef.child(CardID).setValue(card)
                .addOnCompleteListener {
                    Toast.makeText(this, "Card added ", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
}
}
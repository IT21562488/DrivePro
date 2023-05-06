package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private val db = Firebase.firestore
lateinit var spinner: Spinner
private lateinit var bankName: EditText
private lateinit var cityName: EditText
private lateinit var cardNo: EditText
private lateinit var holderName: EditText
lateinit var uDocId: String

class Edit_payment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_payment)

        bankName = findViewById(R.id.idbank)
        cityName = findViewById(R.id.idcity)
        cardNo = findViewById(R.id.idcardNo)
        holderName = findViewById(R.id.idholName)




        spinner = findViewById<Spinner>(R.id.SelectID)
        var arrayList = ArrayList<String>()

        db.collection("Add Bank Details")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                    arrayList.add(document.data.get("Holder Name") as String)
                    val arrayAdapter = ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        arrayList
                    )
                    spinner.adapter = arrayAdapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val dataName = spinner.selectedItem.toString()
                db.collection("Add Bank Details")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            Log.d(TAG, "${document.id} => ${document.data}")
                            val hNAme = document.data.get("Holder Name") as String
                            if (hNAme == dataName) {
                                uDocId = document.id
                                val bankNam = document.data.get("Bank Name").toString()
                                val cityNam = document.data.get("City Name").toString()
                                val cardN = document.data.get("Card No").toString()
                                val holderNam = document.data.get("Holder Name").toString()

                                bankName.setText(bankNam)
                                cityName.setText(cityNam)
                                cardNo.setText(cardN)
                                holderName.setText(holderNam)

                            }

                        }
                    }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        findViewById<Button>(R.id.btnupdate).setOnClickListener(View.OnClickListener {
            val update = mapOf(
                "Bank Name" to bankName.text.toString(),
                "City Name" to cityName.text.toString(),
                "Card No" to cardNo.text.toString(),
                "Holder Name" to holderName.text.toString()
            )
            db.collection("Add Bank Details").document(uDocId).update(update)
            Toast.makeText(this, "Update Success", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,Edit_payment::class.java))
        })
        findViewById<Button>(R.id.btnDelete).setOnClickListener(View.OnClickListener {
            db.collection("Add Bank Details").document(uDocId).delete()
            Toast.makeText(this, "Delete Success", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,Edit_payment::class.java))
        })
        findViewById<ImageView>(R.id.btnbackimg).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,deiver_payment_edit::class.java))
        })
    }

}
package com.example.signupandsignin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.signupandsignin.R

class BankCrdView : AppCompatActivity() {
    private lateinit var idbank: TextView
    private lateinit var idcity: TextView
    private lateinit var idcardNo: TextView
    private lateinit var idholName: TextView
    private lateinit var btnupdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_crd_view)

        initView()
        setValuesToViews()

    }
    private fun initView() {
        idbank = findViewById(R.id.idbank)
        idcity = findViewById(R.id.idcity)
        idcardNo = findViewById(R.id.idcardNo)
        idholName = findViewById(R.id.idholName)

        btnupdate = findViewById(R.id.btnupdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {


        idcity.text = intent.getStringExtra("city")
        idcardNo.text=intent.getStringExtra("crdNo")
        idholName.text=intent.getStringExtra("hname")
        idbank.text= intent.getStringExtra("hname")

    }
}
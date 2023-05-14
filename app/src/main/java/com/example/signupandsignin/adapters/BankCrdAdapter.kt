package com.example.signupandsignin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.signupandsignin.R
import com.example.signupandsignin.models.BankCradModel


class BankCrdAdapter (private val cardList: ArrayList<BankCradModel>):
    RecyclerView.Adapter<BankCrdAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.crd_list, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentcrd = cardList[position]
        holder.idcardNo.text = currentcrd.crdNo

    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val idcardNo : TextView = itemView.findViewById(R.id.idcardNo)


        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }



    }

}
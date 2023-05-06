package com.example.rentalmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalmanagement.R
import com.example.rentalmanagement.models.RentalModel
import com.example.rentalmanagement.models.VehicleModel

class MyRentalAdapter(private val rentalList: ArrayList<RentalModel>):RecyclerView.Adapter<MyRentalAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_rental_list, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentVehicle = rentalList[position]
        holder.tvsTrip.text = currentVehicle.sTrip

    }

    override fun getItemCount(): Int {
        return rentalList.size
    }

    class ViewHolder(itemView: View,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvsTrip : TextView = itemView.findViewById(R.id.tvsTrip)


        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }



    }
}
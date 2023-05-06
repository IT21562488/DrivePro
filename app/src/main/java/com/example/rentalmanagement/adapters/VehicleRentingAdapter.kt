package com.example.rentalmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalmanagement.R
import com.example.rentalmanagement.models.VehicleModel

class VehicleRentingAdapter(private val vehicleList: ArrayList<VehicleModel>):RecyclerView.Adapter<VehicleRentingAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rentingvehicles, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentVehicle = vehicleList[position]
        holder.tvRmodel.text = currentVehicle.vehimodel
        holder.tvRprice.text = currentVehicle.prices
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    class ViewHolder(itemView: View,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvRmodel : TextView = itemView.findViewById(R.id.tvRmodel)
        val tvRprice : TextView = itemView.findViewById(R.id.tvRprice)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }



    }
}
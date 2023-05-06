package com.example.vehiclemanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehiclemanagement.R
import com.example.vehiclemanagement.models.VehicleModel


class VehicleAdapter (private val vehicleList: ArrayList<VehicleModel>):
    RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentVehicle = vehicleList[position]
        holder.tvmodel.text = currentVehicle.vehimodel
//        holder.tvprice.text = currentVehicle.prices
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    class ViewHolder(itemView: View ,clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val tvmodel : TextView = itemView.findViewById(R.id.tvmodel)
//        val tvprice : TextView = itemView.findViewById(R.id.tvprice)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }



    }

}
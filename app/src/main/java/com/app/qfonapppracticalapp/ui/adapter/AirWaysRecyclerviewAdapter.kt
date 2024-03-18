package com.app.qfonapppracticalapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.qfonapppracticalapp.model.Datum
import com.app.qfonapppracticalapp.R
import com.app.qfonapppracticalapp.databinding.ItemAirwaysBinding
import com.squareup.picasso.Picasso

class AirWaysRecyclerviewAdapter(private var list: ArrayList<Datum>) :
    RecyclerView.Adapter<AirWaysRecyclerviewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAirwaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Datum) {
            with(binding) {

                binding.txtAirwaysname.text = item.airline?.get(0)?.name
                binding.txtId.text = item._id.toString()
                binding.txtName.text = item.name
                binding.txtTrips.text = item.trips.toString()
                binding.txtSlogan.text = item.airline?.get(0)?.slogan
                binding.txtCountry.text = item.airline?.get(0)?.country
                binding.txtHeadquarters.text = item.airline?.get(0)?.headQuaters
                binding.txtWebsite.text = item.airline?.get(0)?.website

                Picasso.get()
                    .load(item.airline?.get(0)?.logo)
                    .placeholder(R.drawable.evaairlogo)
                    .error(R.drawable.evaairlogo)
                    .into(binding.imgAirlogo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAirwaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun filter(filterList: ArrayList<Datum>){
       list= filterList
    notifyDataSetChanged()
    }
}
package com.web.testtask.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.web.testtask.data.model.CityModel
import com.web.testtask.databinding.ItemCityBinding

class CitiesPagingDataAdapter(private val callBack: (CityModel) -> Unit) :
    PagingDataAdapter<CityModel, CitiesPagingDataAdapter.CitiesViewHolder>(CitiesEntityDiff()) {

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        getItem(position)?.let { city -> holder.bind(city) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        return CitiesViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class CitiesViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: CityModel) {
            binding.position = city.primaryKey
            binding.name = city.name
            itemView.setOnClickListener {
                callBack(city)
            }
        }
    }

    class CitiesEntityDiff : DiffUtil.ItemCallback<CityModel>() {
        override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel): Boolean =
            oldItem.name == newItem.name
    }
}
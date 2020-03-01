package com.example.weatherapp.ui.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.remote.models.CityProperty
import com.example.weatherapp.databinding.CellCityWeatherBinding

//private val onClickListener: HeroesAdapterListener

class AllCitiesRecyclerViewAdapter(private val onClickListener : CityAdapterListener): ListAdapter<CityProperty, AllCitiesRecyclerViewAdapter.CityWeatherViewHolder>(DiffCallback) {

    fun replaceList(it: List<CityProperty>) {
        println(it)
        for(element in it){
            for(el in currentList){
                if(element.name == el.name){
                    it.drop(it.indexOf(element))
                }
            }
        }
        submitList(it)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<CityProperty>() {
        override fun areItemsTheSame(oldItem: CityProperty, newItem: CityProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CityProperty, newItem: CityProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CityWeatherViewHolder {
        return CityWeatherViewHolder(CellCityWeatherBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CityWeatherViewHolder, position: Int){
        val cityProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(cityProperty)
        }
        holder.bind(cityProperty)
    }

    class CityWeatherViewHolder(private val binding: CellCityWeatherBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cityProperty: CityProperty?){
            binding.cityProperty = cityProperty
            binding.executePendingBindings()
        }
    }

    class CityAdapterListener(val clickListener: (cityProperty: CityProperty?) -> Unit) {
        fun onClick(city: CityProperty) = clickListener(city)
    }
}
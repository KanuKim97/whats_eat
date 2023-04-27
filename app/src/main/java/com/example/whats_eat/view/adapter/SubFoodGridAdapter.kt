package com.example.whats_eat.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whats_eat.view.adapter.adapterItems.SubFoodItems
import com.example.whats_eat.databinding.SubfoodItemBinding

class SubFoodGridAdapter(private val placeList: ArrayList<SubFoodItems>)
    : RecyclerView.Adapter<SubFoodGridAdapter.SubFoodGridViewHolder>() {
    inner class SubFoodGridViewHolder(private val binding: SubfoodItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(results: SubFoodItems) {
            binding.subFoodName.text = results.name
            Glide.with(binding.subFoodView)
                .load(results.photoRef)
                .fitCenter()
                .into(binding.subFoodView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubFoodGridViewHolder {
        val binding = SubfoodItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SubFoodGridViewHolder(binding)
    }

    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(
        holder: SubFoodGridViewHolder,
        position: Int
    ) = holder.bind(placeList[position])

}
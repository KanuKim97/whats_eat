package com.example.whats_eat.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.model.responseModel.Results
import com.example.whats_eat.databinding.SubfoodItemBinding

class SubFoodGridAdapter(private val placeList: Array<Results>)
    : RecyclerView.Adapter<SubFoodGridAdapter.SubFoodGridViewHolder>() {
    inner class SubFoodGridViewHolder(private val binding: SubfoodItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(results: Results) {
            binding.subFoodName.text = results.name

            if (!results.photos.isNullOrEmpty()) {
                Glide.with(binding.subFoodView)
                    .load(results.photos?.get(0)?.photo_reference)
                    .into(binding.subFoodView)
            }
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
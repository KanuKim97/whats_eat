package com.example.whats_eat.view.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whats_eat.data.remote.model.dataClassView.PlaceData
import com.example.whats_eat.databinding.CollectionItemBinding

class CollectionAdapter(private val placeList : ArrayList<PlaceData>)
    : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    inner class CollectionViewHolder(private val binding: CollectionItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(placeList: PlaceData) {
                binding.placeNameTxt.text = placeList.placeName
                binding.placeAddressTxt.text = placeList.placeAddress
                binding.ratingNumTxt.text = placeList.ratingNumber.toString()

                if (placeList.placePhotoRef.isBlank()) { binding.placeImg.visibility = View.GONE }
                else {
                    Glide.with(binding.placeImg)
                        .load(placeList.placePhotoRef)
                        .into(binding.placeImg)
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionViewHolder {
        val binding = CollectionItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CollectionViewHolder,
        position: Int
    ) = holder.bind(placeList[position])

    override fun getItemCount(): Int = placeList.size
}
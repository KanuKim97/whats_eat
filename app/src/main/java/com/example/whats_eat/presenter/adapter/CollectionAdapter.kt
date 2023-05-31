package com.example.whats_eat.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whats_eat.databinding.CollectionItemBinding
import com.example.whats_eat.presenter.ViewModelItems.DetailPlace
import javax.inject.Inject

class CollectionAdapter @Inject constructor(private val placeList: ArrayList<DetailPlace>)
    : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    inner class CollectionViewHolder(
        private val binding: CollectionItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(placeList: DetailPlace) {
            binding.placeNameTxt.text = placeList.name
            binding.placeAddressTxt.text = placeList.formattedAddress
            binding.ratingNumTxt.text = placeList.rating.toString()

            if (placeList.photoRef.isBlank()) {
                binding.placeImg.visibility = View.GONE
            } else {
                Glide.with(binding.placeImg)
                    .load(placeList.photoRef)
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
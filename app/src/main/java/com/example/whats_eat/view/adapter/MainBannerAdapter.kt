package com.example.whats_eat.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whats_eat.data.remote.model.dataViewClass.MainBannerItems
import com.example.whats_eat.databinding.MainbannerItemBinding

class MainBannerAdapter(private val placeList: ArrayList<MainBannerItems>)
    : RecyclerView.Adapter<MainBannerAdapter.MainBannerViewHolder>() {
    inner class MainBannerViewHolder(private val binding: MainbannerItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(placeList: MainBannerItems) {
                binding.MainBannerTxt.text = placeList.name

                Glide.with(binding.MainBannerImg)
                    .load(placeList.photoRef)
                    .fitCenter()
                    .into(binding.MainBannerImg)
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainBannerViewHolder {
        val binding = MainbannerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainBannerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MainBannerViewHolder,
        position: Int
    ) {
        holder.bind(placeList[position])
    }

    override fun getItemCount(): Int = placeList.size

}
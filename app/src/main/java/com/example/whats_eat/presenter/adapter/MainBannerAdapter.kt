package com.example.whats_eat.presenter.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.whats_eat.R
import com.example.whats_eat.presenter.adapter.adapterItems.MainBannerItems
import com.example.whats_eat.databinding.MainbannerItemBinding
import com.example.whats_eat.presenter.FragmentDetailPlace
import javax.inject.Inject

class MainBannerAdapter @Inject constructor(
    private val context: FragmentActivity,
    private val placeList: ArrayList<MainBannerItems>
) : RecyclerView.Adapter<MainBannerAdapter.MainBannerViewHolder>() {

    inner class MainBannerViewHolder(
        private val binding: MainbannerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val fragmentBundle = Bundle()
        private val detailPlaceFragment = FragmentDetailPlace()

        fun bind(placeList: MainBannerItems) {
            binding.MainBannerTxt.text = placeList.name

            Glide.with(binding.MainBannerImg)
                .load(placeList.photoRef)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(binding.MainBannerImg)

            itemView.setOnClickListener {
                fragmentBundle.putString("PlaceID", placeList.placeID)
                detailPlaceFragment.arguments = fragmentBundle

                context.supportFragmentManager.beginTransaction()
                    .replace(R.id.Fragment_container, detailPlaceFragment)
                    .commit()
            }
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
    ) = holder.bind(placeList[position])


    override fun getItemCount(): Int = placeList.size

}
package com.example.whats_eat.presenter.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whats_eat.R
import com.example.whats_eat.presenter.adapter.adapterItems.SubFoodItems
import com.example.whats_eat.databinding.SubfoodItemBinding
import com.example.whats_eat.presenter.FragmentDetailPlace
import javax.inject.Inject

class SubFoodGridAdapter @Inject constructor(
    private val context: FragmentActivity,
    private val placeList: ArrayList<SubFoodItems>
): RecyclerView.Adapter<SubFoodGridAdapter.SubFoodGridViewHolder>() {

    inner class SubFoodGridViewHolder(
        private val binding: SubfoodItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val fragmentBundle: Bundle = Bundle()
        private val detailPlaceFragment: FragmentDetailPlace = FragmentDetailPlace()

        private fun toDetailPlaceFragment(): Int = context.supportFragmentManager
            .beginTransaction()
            .replace(R.id.Fragment_container, detailPlaceFragment)
            .commit()

        fun bind(results: SubFoodItems) {
            binding.subFoodName.text = results.name

            Glide.with(binding.subFoodView)
                .load(results.photoRef)
                .fitCenter()
                .into(binding.subFoodView)

            itemView.setOnClickListener {
                fragmentBundle.putString("PlaceID", results.placeID)
                detailPlaceFragment.arguments = fragmentBundle
                toDetailPlaceFragment()
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
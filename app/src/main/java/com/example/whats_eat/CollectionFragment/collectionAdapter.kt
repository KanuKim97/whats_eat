package com.example.whats_eat.CollectionFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whats_eat.R

class collectionAdapter(private val placeList : ArrayList<placeData>) : RecyclerView.Adapter<collectionAdapter.collectionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): collectionAdapter.collectionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_item, parent, false)

        return collectionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: collectionAdapter.collectionViewHolder, position: Int) {

        val place : placeData = placeList[position]
        holder.placeName.text = place.placeName
        holder.placeAddress.text = place.placeAddress
        holder.placeRating.text = place.ratingNum.toString()

    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    class collectionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val placeName : TextView = itemView.findViewById(R.id.placeName)
        val placeAddress : TextView = itemView.findViewById(R.id.placeAddress)
        val placeRating : TextView = itemView.findViewById(R.id.ratingNum)

    }
}
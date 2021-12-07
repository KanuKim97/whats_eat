package com.example.whats_eat.CollectionFragment

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whats_eat.R

class collectionAdapter(private val placeList : ArrayList<placeData>) : RecyclerView.Adapter<collectionAdapter.collectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): collectionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_item, parent, false)

        return collectionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: collectionViewHolder, position: Int) {

        val place : placeData = placeList[position]

        holder.placeName.text = place.placeName
        holder.placeAddress.text = place.placeAddress
        holder.placeRating.text = place.ratingNum.toString()

        if (place.placePhotoUrl == null) {

            holder.placePhoto.visibility = View.GONE
        } else {

            Glide.with(holder.placePhoto).load(Uri.parse(place.placePhotoUrl)).into(holder.placePhoto)
        }

        holder.delPlaceBtn.setOnClickListener {
            //TODO: deleteBtn onClick Event Need to Write it
            Log.d("Event!", "onClickEvent!")
        }
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    class collectionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val placeName : TextView = itemView.findViewById(R.id.placeName)
        val placeAddress : TextView = itemView.findViewById(R.id.addressName)
        val placeRating : TextView = itemView.findViewById(R.id.ratingNum)
        val placePhoto : ImageView = itemView.findViewById(R.id.placeImage)

        val delPlaceBtn : Button = itemView.findViewById(R.id.collectionDelBtn)
    }
}
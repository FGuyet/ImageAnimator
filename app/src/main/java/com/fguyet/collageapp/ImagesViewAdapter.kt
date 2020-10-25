package com.fguyet.collageapp

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ImagesViewAdapter(private val dataSet: List<Bitmap>) :
    RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_image, parent, false)
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d(TAG, position.toString() + " " + dataSet.size)
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    companion object {
        const val TAG = "ImagesViewAdapter"
    }
}

package com.fguyet.collageapp

import android.graphics.Bitmap
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view_image.view.*

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(bitmap: Bitmap) {
        image.setImageBitmap(bitmap)
    }

    private val image: ImageView = itemView.image_view
    private val checkBox: CheckBox = itemView.checkbox
}

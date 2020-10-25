package com.fguyet.collageapp.ui.selection

import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fguyet.collageapp.R
import kotlinx.android.synthetic.main.item_view_image.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image: ImageView = itemView.image_view
    private val checkBox: CheckBox = itemView.checkbox

    fun bind(url: String, listener: (Boolean) -> Unit) {

        // Asynchrounsly display each image
        image.load(url) {
            crossfade(true)
            placeholder(R.drawable.bugdroid)
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            listener.invoke(isChecked)
            if (isChecked) {
                image.setColorFilter(Color.argb(50, 0, 0, 255))
            } else {
                image.setColorFilter(Color.argb(0, 0, 0, 0))
            }
        }

        // Toggle selected state when clicking anywhere on the item
        itemView.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked
        }
    }
}

package com.fguyet.collageapp

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class ImagesViewAdapter(private val dataSet: List<Bitmap>) :
    RecyclerView.Adapter<ImageViewHolder>() {

    private val _selectedImagesStateFlow = MutableStateFlow(emptyList<Bitmap>())
    val selectedImagesStateFlow: StateFlow<List<Bitmap>> = _selectedImagesStateFlow
    var selectedImages: List<Bitmap>
        get() = _selectedImagesStateFlow.value
        private set(value) {
            _selectedImagesStateFlow.value = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_image, parent, false)
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val bitmap = dataSet[position]
        holder.bind(bitmap) { isSelected ->
            selectedImages = if (isSelected) {
                selectedImages.toMutableList().apply { add(bitmap) }
            } else {
                selectedImages.toMutableList().apply { remove(bitmap) }
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size
}

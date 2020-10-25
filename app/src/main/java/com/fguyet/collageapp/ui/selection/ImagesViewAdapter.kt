package com.fguyet.collageapp.ui.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fguyet.collageapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class ImagesViewAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<ImageViewHolder>() {

    private val _selectedImagesURLsStateFlow = MutableStateFlow(emptyList<String>())
    val selectedImagesURLsStateFlow: StateFlow<List<String>> = _selectedImagesURLsStateFlow

    var selectedImagesURLs: List<String>
        get() = _selectedImagesURLsStateFlow.value
        private set(value) {
            _selectedImagesURLsStateFlow.value = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_image, parent, false))

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = dataSet[position]
        holder.bind(url) { isSelected ->
            selectedImagesURLs = if (isSelected) {
                selectedImagesURLs.toMutableList().apply { add(url) }
            } else {
                selectedImagesURLs.toMutableList().apply { remove(url) }
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size
}

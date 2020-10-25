package com.fguyet.collageapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_image_selection.*

class ImageSelectionFragment : Fragment() {
    private val dataSet: List<Bitmap> by lazy {
        listOf(
            //TODO replace by real data, asynchronously
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid),
            BitmapFactory.decodeResource(context?.resources, R.drawable.bugdroid)
        )
    }
    private val imagesAdapter by lazy { ImagesViewAdapter(dataSet) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_image_selection, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_text.text = getString(R.string.your_research, arguments?.getString("searchText"))

        images_recycler_view.apply {
            layoutManager = GridLayoutManager(context, GRID_LAYOUT_NUMBER_OF_COLUMNS)
            adapter = imagesAdapter
        }
    }

    companion object {
        const val GRID_LAYOUT_NUMBER_OF_COLUMNS = 2

    }
}
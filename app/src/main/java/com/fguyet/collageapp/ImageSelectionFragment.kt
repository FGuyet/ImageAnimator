package com.fguyet.collageapp

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_image_selection.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageSelectionFragment : Fragment() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val dataSet = mutableListOf<Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_image_selection, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = getString(R.string.your_research, arguments?.getString("searchText"))
        search_text.text = query

        images_recycler_view.apply {
            layoutManager = GridLayoutManager(context, GRID_LAYOUT_NUMBER_OF_COLUMNS)
            val imagesViewAdapter = ImagesViewAdapter(dataSet)

            uiScope.launch {
                dataSet.addAll(ImageProvider.getImages(query, context))
                imagesViewAdapter.notifyDataSetChanged()
            }
            adapter = imagesViewAdapter
        }

        animate_button.setOnClickListener {
            val bundle = bundleOf(
                "query" to query,
                "imageIds" to arrayListOf(1, 2, 3)
            )
            findNavController().navigate(
                R.id.action_imageSelectionFragment_to_animationFragment,
                bundle
            )
        }
    }

    companion object {
        const val GRID_LAYOUT_NUMBER_OF_COLUMNS = 2

    }
}
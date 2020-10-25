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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ImageSelectionFragment : Fragment() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val dataSet = mutableListOf<Bitmap>()
    var imagesViewAdapter by Delegates.notNull<ImagesViewAdapter>()

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
            imagesViewAdapter = ImagesViewAdapter(dataSet)

            uiScope.launch {
                dataSet.clear()
                dataSet.addAll(ImageProvider.getImages(query, context))
                imagesViewAdapter.notifyDataSetChanged()
            }
            adapter = imagesViewAdapter
        }

        uiScope.launch {
            imagesViewAdapter.selectedImagesStateFlow.collect {
                // Enable button only if at least 2 images are selected
                animate_button.isEnabled = (it.size >= 2)
            }
        }

        animate_button.setOnClickListener {
            val imageIds = imagesViewAdapter.selectedImages.map {
                dataSet.indexOf(it)
            }

            val bundle = bundleOf(
                "query" to query,
                "imageIds" to imageIds
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
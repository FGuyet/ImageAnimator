package com.fguyet.collageapp.ui.selection

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fguyet.collageapp.R
import com.fguyet.collageapp.data.PixabayImageProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_image_selection.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ImageSelectionFragment : Fragment() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val dataSet = mutableListOf<String>()
    var imagesViewAdapter by Delegates.notNull<ImagesViewAdapter>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_image_selection, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString(context?.resources?.getString(R.string.key_query))
            ?: throw IllegalStateException("Query cannot be null")

        search_text.text = getString(R.string.your_research, query)

        images_recycler_view.apply {
            layoutManager = GridLayoutManager(context, GRID_LAYOUT_NUMBER_OF_COLUMNS)
            imagesViewAdapter = ImagesViewAdapter(dataSet)

            uiScope.launch {
                dataSet.clear()
                val elements = PixabayImageProvider.getImagesURL(query)

                if (elements.size < 2) {
                    // Not enough results, going back to research fragment
                    Snackbar.make(view, context.getString(R.string.not_enough_results), Snackbar.LENGTH_LONG).apply {
                        this.view.setBackgroundColor(Color.RED)
                        this.show()
                    }
                    findNavController().navigate(R.id.action_imageSelectionFragment_to_searchFragment)
                } else {
                    dataSet.addAll(elements)
                    imagesViewAdapter.notifyDataSetChanged()
                }

            }
            adapter = imagesViewAdapter
        }

        uiScope.launch {
            imagesViewAdapter.selectedImagesURLsStateFlow.collect {
                // Enable button only if at least 2 images are selected
                animate_button.isEnabled = (it.size >= 2)
            }
        }

        animate_button.setOnClickListener {
            val imagesURLsKey = context?.getString(R.string.key_images_urls)
                ?: throw java.lang.IllegalStateException("imagesURLsKey cannot be null")

            val queryKey = context?.getString(R.string.key_query)
                ?: throw java.lang.IllegalStateException("queryKey cannot be null")

            val bundle = bundleOf(
                queryKey to query,
                imagesURLsKey to imagesViewAdapter.selectedImagesURLs
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
package com.fguyet.collageapp

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.item_view_image.*
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlin.properties.Delegates

@InternalCoroutinesApi
class AnimationFragment : Fragment() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private var currentAnimation: Job? = null
    private var query by Delegates.notNull<String>()
    private var imageIndexes by Delegates.notNull<ArrayList<Int>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query =
            arguments?.getString("query") ?: throw IllegalArgumentException("Query cannot be null")
        imageIndexes = arguments?.getIntegerArrayList("imageIds") ?: throw IllegalArgumentException(
            "imageIds cannot be null"
        )
    }

    override fun onResume() {
        super.onResume()
        val selectedImages =
            ImageProvider.getCachedImages(query).filterIndexed { index, _ -> index in imageIndexes }
        currentAnimation = uiScope.launch { loopThrough(selectedImages) }
    }

    override fun onPause() {
        currentAnimation?.cancel()
        super.onPause()
    }

    private suspend fun loopThrough(images: List<Bitmap>) {
        while (isActive) {
            images.forEach {
                image_view.setImageBitmap(it)
                delay(IMAGE_DISPLAY_DURATION)
            }
        }
    }

    companion object {
        const val IMAGE_DISPLAY_DURATION = 2_000L
    }
}
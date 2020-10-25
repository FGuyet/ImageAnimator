package com.fguyet.collageapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
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
    private var imagesURLs by Delegates.notNull<ArrayList<String>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query =
            arguments?.getString("query") ?: throw IllegalArgumentException("Query cannot be null")
        imagesURLs = arguments?.getStringArrayList("imagesURLs") ?: throw IllegalArgumentException(
            "imagesURLs cannot be null"
        )
    }

    override fun onResume() {
        super.onResume()
        currentAnimation = uiScope.launch { loopThrough(imagesURLs) }
    }

    override fun onPause() {
        currentAnimation?.cancel()
        super.onPause()
    }

    private suspend fun loopThrough(images: ArrayList<String>) {
        while (isActive) {
            images.forEach {
                image_view.load(it)
                delay(IMAGE_DISPLAY_DURATION)
            }
        }
    }

    companion object {
        const val IMAGE_DISPLAY_DURATION = 2_000L
    }
}
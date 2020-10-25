package com.fguyet.collageapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImageProvider.clearCache()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_button.setOnClickListener {
            // Close Keyboard
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            val queryKey = context?.getString(R.string.key_query)
                ?: throw java.lang.IllegalStateException("queryKey cannot be null")

            val query: String = image_search_input.text.toString()

            val bundle = bundleOf(queryKey to query)
            view.findNavController()
                .navigate(R.id.action_searchFragment_to_imageSelectionFragment, bundle)
        }
    }

}
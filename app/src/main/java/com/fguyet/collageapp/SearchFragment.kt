package com.fguyet.collageapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_button.setOnClickListener {

            val searchText: String = image_search_input.text.toString()
            val bundle = bundleOf("searchText" to searchText)
            view.findNavController()
                .navigate(R.id.action_searchFragment_to_imageSelectionFragment, bundle)
        }
    }

}
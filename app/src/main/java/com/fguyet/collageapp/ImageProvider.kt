package com.fguyet.collageapp

import android.util.Log
import org.json.JSONObject

object ImageProvider {
    suspend fun getImagesURL(query: String, maxResult: Int = MAX_RESULT_DEFAULT): List<String> {
        val response = PixabayService.searchImages(query)
        val hits = response.getJSONArray("hits")

        val maxIndex = minOf(hits.length(), maxResult)

        val imagesURLs = mutableListOf<String>()

        var index = 0
        while (index < maxIndex) {
            Log.i("TAG", index.toString())
            imagesURLs.add((hits.get(index) as JSONObject).getString("webformatURL"))
            index++
        }

        return imagesURLs
    }

    private const val MAX_RESULT_DEFAULT = 20
}
package com.fguyet.collageapp

import android.graphics.*
import android.util.Log
import org.json.JSONObject

object ImageProvider {
    private val cachedImages = mutableMapOf<String, List<Bitmap>>()

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

    private fun tintImage(bitmap: Bitmap, color: Int): Bitmap {
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        val bitmapResult = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapResult)
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint)
        return bitmapResult
    }

    fun getCachedImages(query: String): List<Bitmap> = cachedImages[query] ?: emptyList()

    fun clearCache() {
        cachedImages.clear()
    }

    const val MAX_RESULT_DEFAULT = 20
}
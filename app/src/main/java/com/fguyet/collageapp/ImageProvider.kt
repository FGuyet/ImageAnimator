package com.fguyet.collageapp

import android.content.Context
import android.graphics.*

object ImageProvider {
    private val cachedImages = mutableMapOf<String, List<Bitmap>>()

    // TODO load images from API
    // TODO remove temporary context parameter
    suspend fun getImages(query: String, context: Context?): List<Bitmap> {
        if (context == null) throw IllegalStateException("Context cannot be null")

        val initialBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bugdroid)

        val images = listOf(initialBitmap,
                tintImage(initialBitmap, Color.BLACK),
                tintImage(initialBitmap, Color.BLUE),
                tintImage(initialBitmap, Color.YELLOW),
                tintImage(initialBitmap, Color.GRAY),
                tintImage(initialBitmap, Color.MAGENTA),
                tintImage(initialBitmap, Color.CYAN)
        )
        cachedImages[query] = images
        return images
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
}
package com.fguyet.collageapp.data

import android.util.Log
import com.fguyet.collageapp.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

// TODO: Split to interface & implementation to have several imageProvider --> Easier to test and to scale
object PixabayImageProvider {

    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    suspend fun getImagesURL(query: String, maxResult: Int = MAX_RESULT_DEFAULT): List<String> {
        val response = searchImages(query)
        val hits = response.getJSONArray("hits")

        return mutableListOf<String>().apply {
            var index = 0
            while (index < minOf(hits.length(), maxResult)) {
                this.add((hits.get(index) as JSONObject).getString(IMAGE_URL_FIELD))
                index++
            }
        }
    }

    private suspend fun searchImages(query: String): JSONObject = withContext(ioScope.coroutineContext) {
        val uri = "$PIXABAY_URL?key=${BuildConfig.PIXABAY_KEY}&q=${URLEncoder.encode(query, "UTF-8")}"
        Log.i(TAG, "query on: $uri")

        val url = URL(uri)
        val connection = url.openConnection() as HttpURLConnection
        val responseText = connection.inputStream.bufferedReader().use(BufferedReader::readText)
        JSONObject(responseText)
    }

    private const val TAG = "PixabayImageProvider"
    private const val PIXABAY_URL = "https://pixabay.com/api/"
    private const val IMAGE_URL_FIELD = "webformatURL"
    private const val MAX_RESULT_DEFAULT = 20
}
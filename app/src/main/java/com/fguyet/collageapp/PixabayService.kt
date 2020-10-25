package com.fguyet.collageapp

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


object PixabayService {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    suspend fun searchImages(query: String): JSONObject =
        withContext(ioScope.coroutineContext) {
            val uri =
                "$PIXABAY_URL?key=${BuildConfig.PIXABAY_KEY}&q=${URLEncoder.encode(query, "UTF-8")}"
            Log.i("TAG", uri)

            val url = URL(uri)
            val connection = url.openConnection() as HttpURLConnection
            val responseText = connection.inputStream.bufferedReader().use(BufferedReader::readText)
            JSONObject(responseText)
        }

    private const val PIXABAY_URL = "https://pixabay.com/api/"
}
package com.furkanmulayim.gorseldenmetincikar.data.service.gpt

import com.furkanmulayim.gorseldenmetincikar.config.GptAPIConfig
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class APIClient {
    //Buraya kendi API anahtarınızı girin..
    private val apiKey = GptAPIConfig().apiKey
    private val url = "https://api.openai.com/v1/engines/gpt-3.5-turbo-instruct-0914/completions"
    private val client = OkHttpClient()

    fun post(requestBody: String, callback: Callback) {
        val request = Request.Builder().url(url).header("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull())).build()
        client.newCall(request).enqueue(callback)
    }
}
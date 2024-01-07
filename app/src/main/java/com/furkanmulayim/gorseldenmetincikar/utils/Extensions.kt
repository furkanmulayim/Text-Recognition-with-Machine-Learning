package com.furkanmulayim.gorseldenmetincikar.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.furkanmulayim.gorseldenmetincikar.data.service.gpt.APIClient
import com.furkanmulayim.gorseldenmetincikar.data.service.gpt.RequestBodyBuilder
import com.furkanmulayim.gorseldenmetincikar.data.service.gpt.ResponseParser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

fun View.hideKeyboard() {
//Klavye Kapatmak için
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.showMessage(message: String) {
    //kullanıcıya mesaj göstermek için
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun getGPTResponse(gptQuery: String, callback: (String) -> Unit) {
    val apiClient = APIClient()
    val requestBodyBuilder = RequestBodyBuilder()
    val responseParser = ResponseParser()

    val requestBody = requestBodyBuilder.build(gptQuery)

    apiClient.post(requestBody, object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e(e.message, "Request Hatası!")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            val textResult = responseParser.parse(body!!)
            callback(textResult!!)
        }
    })
}
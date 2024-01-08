package com.furkanmulayim.gorseldenmetincikar.presentation.story

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.model.Message
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import com.furkanmulayim.gorseldenmetincikar.utils.getGPTResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StoryViewModel(application: Application) : BaseViewModel(application) {

    val messageList = mutableListOf<Message>()
    val dataGeldiMi = MutableLiveData<Boolean>()

    fun getGPTresponse(query: String) {
        getGPTResponse("$query ") { response ->
            messageList.add(Message(response.trim(), false))
            dataGeldiMi.postValue(true)
        }
    }
}
package com.furkanmulayim.gorseldenmetincikar.presentation.story

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.furkanmulayim.gorseldenmetincikar.model.Message
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import com.furkanmulayim.gorseldenmetincikar.utils.getGPTResponse

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
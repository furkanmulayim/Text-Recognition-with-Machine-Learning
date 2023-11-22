package com.furkanmulayim.gorseldenmetincikar.presentation.history

import android.app.Application
import android.view.View
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import com.furkanmulayim.gorseldenmetincikar.utils.SharedPrefs

class HistoryViewModel(application: Application): BaseViewModel(application) {

    private var sp = SharedPrefs(getApplication())

    fun navigate(view: View, pageId: Int) {
        Navigation.findNavController(view).navigate(pageId)
    }

    fun gorselUriKaydet(imageUri: String, ) {
        sp.saveImageLocation(imageUri)
    }

}
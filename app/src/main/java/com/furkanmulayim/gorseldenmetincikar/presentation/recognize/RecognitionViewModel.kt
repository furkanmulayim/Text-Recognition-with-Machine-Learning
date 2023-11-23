package com.furkanmulayim.gorseldenmetincikar.presentation.recognize

import android.app.Application
import android.net.Uri
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import com.furkanmulayim.gorseldenmetincikar.utils.SharedPrefs

class RecognitionViewModel(application: Application) : BaseViewModel(application) {

    //shared preferences nesnesi
    private var sp = SharedPrefs(getApplication())



    //gelen urlyi parse etmek
    fun parsUrl(): Uri {
        //Uri türünden görselimi var
        return Uri.parse(sp.getImageUriInShared())
    }

    fun navigate(view: View, pageId: Int) {
        Navigation.findNavController(view).navigate(pageId)
    }

}
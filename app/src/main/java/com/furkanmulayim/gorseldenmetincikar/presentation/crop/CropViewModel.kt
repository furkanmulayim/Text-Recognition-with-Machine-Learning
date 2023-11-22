package com.furkanmulayim.gorseldenmetincikar.presentation.crop

import android.app.Application
import android.view.View
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import com.furkanmulayim.gorseldenmetincikar.utils.SharedPrefs

class CropViewModel(application: Application) : BaseViewModel(application) {

    //shared preferences nesnesi
    private var sp = SharedPrefs(getApplication())

    //gelen urlyi parse etmek
    fun getUrl(): String? {
        //Uri türünden görselimi var
        return sp.getImageUriInShared()
    }

    fun navigate(view: View, pageId: Int) {
        Navigation.findNavController(view).navigate(pageId)
    }

    fun setImageUri(toString: String) {
        sp.saveImageLocation(toString)
    }

}
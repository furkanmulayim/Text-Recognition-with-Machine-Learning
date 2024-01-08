package com.furkanmulayim.gorseldenmetincikar.presentation.hello

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import com.furkanmulayim.gorseldenmetincikar.utils.SharedPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HelloViewModel(application: Application): BaseViewModel(application) {

    private var sp = SharedPrefs(getApplication())
    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> get() = _isInternetAvailable

     fun checkInternetConnection() {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = activeNetwork?.let { connectivityManager.getNetworkCapabilities(it) }

        _isInternetAvailable.value = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
    fun baglantiVar(view: View) {
            Navigation.findNavController(view).navigate(R.id.action_helloFragment2_to_storyFragment)
    }


    fun navigate(view: View, pageId: Int) {
        Navigation.findNavController(view).navigate(pageId)
    }

    fun gorselUriKaydet(imageUri: String, ) {
        sp.saveImageLocation(imageUri)
    }

}